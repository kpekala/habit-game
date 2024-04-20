import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthResponse } from "src/app/auth/auth-response.model";
import { AuthService } from "src/app/auth/auth.service";
import { environment } from "src/environment/environment";
import { PlayerItemDto, UserResponse } from "./user.model";

@Injectable({
    providedIn: 'root'
  })
export class UserService {

    private userPath = environment.backendPath + "api/user"
    private itemsPath = environment.backendPath + "api/player/items"

    constructor(private http: HttpClient, private authService: AuthService) {

    }

    public fetchUserInformation(): Observable<UserResponse> {
      const params = {'email': this.authService.getEmail()};
      return this.http.get<UserResponse>(this.userPath, {params: params});
    }

    public fetchPlayerItems(): Observable<PlayerItemDto[]> {
      const params = {'email': this.authService.getEmail()};
      return this.http.get<PlayerItemDto[]>(this.itemsPath, {params: params});
    }

}