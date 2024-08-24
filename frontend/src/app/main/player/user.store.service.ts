import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { UserResponse } from "./user.model";

@Injectable({providedIn: 'root'})
export class UserStoreService {
    private readonly _user = new BehaviorSubject<UserResponse>(null);
    readonly user$ = this._user.asObservable();

    setUser(user: UserResponse) {
        this._user.next(user);
    }
}