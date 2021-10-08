import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

export interface IUser {
  id: number;
  email: string;
  avatarUrl?: string
}

const defaultPath = '/';

@Injectable()
export class AuthService {
  private _user: IUser | null = null;
  get loggedIn(): boolean {
    return !!this._user;
  }

  private _lastAuthenticatedPath: string = defaultPath;
  set lastAuthenticatedPath(value: string) {
    this._lastAuthenticatedPath = value;
  }

  constructor(private router: Router, private http: HttpClient, private cookieService: CookieService) { }

  httpOptions(username: String, password: String) {
    return {
        headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic ' + btoa(username + ":" + password)
      }),
      withCredentials: true, 
      observe: 'response' as 'response'
    };
  }

  async logIn(email: string, password: string) {

    try {
      let user = await this.http.post<IUser>("http://localhost:8081/users/login", null, this.httpOptions(email, password)).toPromise();
      this._user = user.body

      this.router.navigate([this._lastAuthenticatedPath]);

      return {
        isOk: true,
        data: this._user
      };
    }
    catch(e) {
      console.log(e)
      return {
        isOk: false,
        message: "Authentication failed"
      };
    }
  }

  async getUser() {
      let user: Observable<IUser> = this.http.get<IUser>("http://localhost:8081/users/current", {withCredentials: true})
      return user.toPromise();
  }

  async createAccount(email: string, password: string) {
    try {
      // Send request
      console.log(email, password);

      this.router.navigate(['/create-account']);
      return {
        isOk: true
      };
    }
    catch {
      return {
        isOk: false,
        message: "Failed to create account"
      };
    }
  }

  async changePassword(email: string, recoveryCode: string) {
    try {
      // Send request
      console.log(email, recoveryCode);

      return {
        isOk: true
      };
    }
    catch {
      return {
        isOk: false,
        message: "Failed to change password"
      }
    };
  }

  async resetPassword(email: string) {
    try {
      // Send request
      console.log(email);

      return {
        isOk: true
      };
    }
    catch {
      return {
        isOk: false,
        message: "Failed to reset password"
      };
    }
  }

  async logOut() {
    await this.http.post<void>("http://localhost:8081/users/logout", null, {withCredentials: true  }).toPromise();

    this._user = null;
    this.cookieService.deleteAll();
    this.router.navigate(['/login-form']);
  }
}

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private authService: AuthService) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const isLoggedIn = this.authService.loggedIn;
    const isAuthForm = [
      'login-form',
      'reset-password',
      'create-account',
      'change-password/:recoveryCode'
    ].includes(route.routeConfig?.path || defaultPath);

    if (isLoggedIn && isAuthForm) {
      this.authService.lastAuthenticatedPath = defaultPath;
      this.router.navigate([defaultPath]);
      return false;
    }

    if (!isLoggedIn && !isAuthForm) {
      this.router.navigate(['/login-form']);
    }

    if (isLoggedIn) {
      this.authService.lastAuthenticatedPath = route.routeConfig?.path || defaultPath;
    }

    return isLoggedIn || isAuthForm;
  }
}
