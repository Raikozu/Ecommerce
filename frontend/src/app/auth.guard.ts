import { Injectable } from '@angular/core';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, UrlTree } from '@angular/router';
import { SellerService } from './services/seller.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private sellerService:SellerService) {}

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot):Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{
    // La tua logica di autenticazione e controllo dei permessi
    return this.sellerService.isSellerLoggedIn; // Ritorna true se l'utente ha l'autenticazione o i permessi appropriati
  }
}