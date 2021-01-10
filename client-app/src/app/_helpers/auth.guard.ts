import {Injectable} from '@angular/core';
import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { AuthenticationService } from '../auth/authentication.service';
import { AuthService } from '../auth/authservices/auth.service';


@Injectable({

providedIn : 'root'

})
export class AuthGuard implements CanActivate {

constructor (private authenticationService : AuthenticationService, private router: Router) {}

canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
        // logged in so return true
        return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
}



}
