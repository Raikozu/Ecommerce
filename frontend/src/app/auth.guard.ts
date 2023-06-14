import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, UrlTree } from '@angular/router';
import { SellerService } from './services/seller.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor() {}

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot):Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{
    if(localStorage.getItem("utente") || localStorage.getItem("admin")){
      return true;
    }
    return false; // Ritorna true se l'utente ha l'autenticazione o i permessi appropriati
  }
}