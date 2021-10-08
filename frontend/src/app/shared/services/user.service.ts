import { Output, Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { IUser } from '.';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class UserService {
  @Output() changed = new EventEmitter();

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  public async fetchCurrentUserInfo(): Promise<IUser>{
    let user: Observable<IUser> = this.http.get<IUser>("http://localhost:8081/users/current", {withCredentials: true})
    return user.toPromise();
  }

  private httpOptions(username: String, password: String) {
    return {
        headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic ' + btoa(username + ":" + password)
      }),
      withCredentials: true, 
      observe: 'response' as 'response'
    };
  }

  public async login(email: String, password: String): Promise<IUser | null>{
    let user = await this.http.post<IUser>("http://localhost:8081/users/login", null, this.httpOptions(email, password)).toPromise();
    return user.body;
  }

  public async logout(){
    await this.http.post<void>("http://localhost:8081/users/logout", null, {withCredentials: true  }).toPromise();
  }

  public async createUser(email: String, password: String): Promise<IUser>{
    return this.http.post<IUser>("http://localhost:8081/users/create", {email: email, password: password}).toPromise();
  }

}
