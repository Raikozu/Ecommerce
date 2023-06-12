import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, of } from 'rxjs';
import { ApiService } from '../services/api.service';
import { API } from '../constants';
import { Router } from '@angular/router';
import { SellerService } from '../services/seller.service';

@Component({
  selector: 'app-seller-auth',
  templateUrl: './seller-auth.component.html',
  styleUrls: ['./seller-auth.component.css']
})
export class SellerAuthComponent implements OnInit{

  constructor(private sellerService:SellerService) {}

  ngOnInit(): void {
    this.sellerService.reloadSeller()
  }

  signUp(data:object):void{
    this.sellerService.signUp(data);
  }
}
