import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthResponse } from "src/app/auth/auth-response.model";
import { AuthService } from "src/app/auth/auth.service";
import { environment } from "src/environment/environment";
import { UserResponse } from "./user.model";

@Injectable({
    providedIn: 'root'
  })
export class UserService {

    private authPath = environment.backendPath + "api/user"

    constructor(private http: HttpClient, private authService: AuthService) {

    }

    public fetchUserInformation(): Observable<UserResponse> {
      const token = this.authService.getTokenHeader();
      const params = {'email': this.authService.getEmail()};
        return this.http.get<UserResponse>(this.authPath, {params: params});
    }
}