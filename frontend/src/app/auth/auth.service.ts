import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { tap } from "rxjs";
import { environment } from "src/environment/environment";
import { AuthResponse } from "./auth-response.model";

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
            .pipe(tap((response: AuthResponse) => {
                this.saveAuthDataLocally(response);
            }));
    }

    public signin(email: string, password: string) {
        const body = {
            emailAddress: email,
            password: password
        };
        return this.http.post(this.authPath + 'signin', body)
            .pipe(tap((response: AuthResponse) => {
                this.saveAuthDataLocally(response);
            }));
    }

    saveAuthDataLocally(response: AuthResponse) {
        localStorage.setItem('token', response.token);
        localStorage.setItem('expiration', response.tokenExpirationDate);
    }
    
}