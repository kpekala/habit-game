import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

export interface SnackbarData {
    text: string;
    type: string;
}

@Injectable({
    providedIn: 'root'
})
export class SnackbarService {
    $newSnackbar = new Subject<SnackbarData>();

    success(text: string) {
        this.$newSnackbar.next({text: text, type: 'success'});
    }

    info(text: string) {
        this.$newSnackbar.next({text: text, type: 'info'});
    }

    error(text: string) {
        this.$newSnackbar.next({text: text, type: 'error'});
    }
}