import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from './user.service';

export interface IUser {
  id: number;
  email: string;
  avatarUrl?: string
}

const defaultPath = '/';

@Injectable()
export class AuthService {
  private _user: IUser | null = null;
  loggedIn(): boolean {
      return localStorage.getItem("logged") === "true"
  }

  private _lastAuthenticatedPath: string = defaultPath;
  set lastAuthenticatedPath(value: string) {
    this._lastAuthenticatedPath = value;
  }

  constructor(private router: Router, private http: HttpClient, private cookieService: CookieService, private userService: UserService) { }

  async logIn(email: string, password: string) {

    try {
      this._user = await this.userService.login(email, password);
      localStorage.setItem("logged", "true");

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
    return this.userService.fetchCurrentUserInfo();
  }

  async createAccount(email: string, password: string) {
    try {
      await this.userService.createUser(email, password);

      this.router.navigate(['/login']);
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
    try{
      await this.userService.logout();
    } catch(e){
      //user is already logged out
    }

    localStorage.clear();
    this._user = null;
    this.cookieService.deleteAll();
    this.router.navigate(['/login-form']);
  }
}

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private authService: AuthService) { }

  async canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
    const isLoggedIn = this.authService.loggedIn();
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
