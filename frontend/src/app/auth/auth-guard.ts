import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "./auth.service";

export const canActivateAuthContent: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router)
    if (authService.isUserLoggedIn())
        return true;
    router.navigate(["/auth"]); 
    return false;
}

export const goToMainIfAuthenticated: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router)
    if (!authService.isUserLoggedIn())
        return true;
    router.navigate(["/main"]); 
    return false;
}