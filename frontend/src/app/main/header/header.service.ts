import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class HeaderService {
    updateHeader = new Subject<void>();
}