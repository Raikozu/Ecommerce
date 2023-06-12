import { Injectable } from '@angular/core';
import { BehaviorSubject, of, tap } from 'rxjs';
import { ApiService } from './api.service';
import { API } from '../constants';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class SellerService {

  isSellerLoggedIn=new BehaviorSubject<boolean>(false);
  constructor(private apiService:ApiService, private router:Router) {}

  signUp(data:object):void {
    let result =this.apiService.makeRequest("post",API.utente+API.registrati,data)
    result.subscribe((response) => {
      localStorage.setItem("seller",JSON.stringify(response))
      this.isSellerLoggedIn.next(true)
      this.router.navigate(['seller-home']);
    })
  }

  reloadSeller():void{
    if(localStorage.getItem("seller")){
      this.isSellerLoggedIn.next(true);
      this.router.navigate(['seller-home']);
    }
  }
}
