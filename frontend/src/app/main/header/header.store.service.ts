import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";

@Injectable({providedIn: 'root'})
export class HeaderStoreService {
    private readonly _updateHeader = new Subject();
    readonly updateHeader$ = this._updateHeader.asObservable();

    update() {
        this._updateHeader.next(null);
    }
}