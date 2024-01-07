import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { tap } from "rxjs";
import { environment } from "src/environment/environment";

@Injectable({
    providedIn: 'root'
  })
export class AuthService {

    private authPath = environment.backendPath + "api/auth/"

    constructor(private http: HttpClient) {

    }

    public signup(email: string, userName: string, password: string) {
        const body = {
            emailAddress: email,
            name: userName,
            password: password
        };
        return this.http.post(this.authPath + 'signup', body)
            .pipe(tap((response: {token: string}) => {
                this.setUserLoggedIn(response.token);
            }));
    }

    setUserLoggedIn(token: string) {
        localStorage.setItem('token', token);
    }
    
}