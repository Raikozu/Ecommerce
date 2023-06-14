import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';
import { API } from '../constants';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private apiService:ApiService, private router:Router) {}

  isSellerLoggedIn=new BehaviorSubject<boolean>(false);
  ngOnInit(){
      if(localStorage.getItem("admin")){
        this.isSellerLoggedIn.next(true);
        this.router.navigate(['admin']);
      }
  }
  login(data:object):void{
    let result =this.apiService.makeRequest("post",API.utente+API.accedi,data)
    result.subscribe((response) => {
      localStorage.setItem("admin",JSON.stringify(response))
      this.isSellerLoggedIn.next(true)
      this.router.navigate(['admin']);
    })
  }
}
