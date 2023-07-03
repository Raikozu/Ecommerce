import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';
import { API } from '../constants';
import { BehaviorSubject } from 'rxjs';
import jwtDecode from 'jwt-decode';

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
      const token= response.token;
      const tokenData= JSON.stringify(response);
      const decodedToken = jwtDecode(token) as { ruolo: string };
      const ruolo= decodedToken.ruolo;
      if (ruolo === "ADMIN") {
        localStorage.setItem("admin",tokenData);
        this.router.navigate(['admin']);
        this.isSellerLoggedIn.next(true);
      }
      else {
        localStorage.setItem("utente",tokenData);
      }
    })
  }
}
