import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, of } from 'rxjs';
import { ApiService } from '../api.service';
import { API } from '../constants';

@Component({
  selector: 'app-seller-auth',
  templateUrl: './seller-auth.component.html',
  styleUrls: ['./seller-auth.component.css']
})
export class SellerAuthComponent implements OnInit{

  constructor(private apiService:ApiService) {}

  ngOnInit(): void {
      
  }

  signUp(data:object): void{
    this.apiService.makeRequest('post',API.utente+API.registrati,data).subscribe((d)=>{
      console.warn(d);
    })
  }
}
