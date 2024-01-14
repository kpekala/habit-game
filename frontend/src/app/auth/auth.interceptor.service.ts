import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpParams, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, take, exhaustMap } from "rxjs";
import { AuthService } from "./auth.service";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor{ 

    constructor(private authService: AuthService) {
        
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(this.authService.isUserLoggedIn()) {
            const token = this.authService.getTokenHeader();
            const headers = new HttpHeaders().set('Authorization', token);
            const modifiedRequest = req.clone({headers: headers});
            return next.handle(modifiedRequest);
        }
        return next.handle(req);
    }
}