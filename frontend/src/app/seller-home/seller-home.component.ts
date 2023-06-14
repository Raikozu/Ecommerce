import { Component } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-seller-home',
  templateUrl: './seller-home.component.html',
  styleUrls: ['./seller-home.component.css']
})
export class SellerHomeComponent {
  email: string ="";

  constructor(private router: Router) {
    const token = localStorage.getItem('seller');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      this.email = decodedToken.sub;
    }
  }

  login(): void {
    this.router.navigate(["login"]);
  }
}
