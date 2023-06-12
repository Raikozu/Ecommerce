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
      this.isSellerLoggedIn.next(true)
      if(result){
        this.router.navigate(['seller-home']);
      }
    })
    console.log(result)
  }
}
