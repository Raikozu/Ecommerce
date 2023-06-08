import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-seller-auth',
  templateUrl: './seller-auth.component.html',
  styleUrls: ['./seller-auth.component.css']
})
export class SellerAuthComponent implements OnInit{

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
      
  }

  signUp(data:object): void{
    this.http.post('http://localhost:8080/utente/registrati',data).subscribe((d)=>{console.warn(d)});
  }
}
