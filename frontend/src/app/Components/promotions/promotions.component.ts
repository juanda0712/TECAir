import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Promotion } from 'src/app/Interfaces/execution';
import { ApiService } from 'src/app/Services/api-service';

@Component({
  selector: 'app-promotions',
  templateUrl: './promotions.component.html',
  styleUrls: ['./promotions.component.css'],
})
export class PromotionsComponent {
  promotions: Promotion[] = [];

  constructor(private api: ApiService<Promotion>, private router: Router) {}

  ngOnInit() {
    this.api.getAll('Promotion').subscribe((promotions: Promotion[]) => {
      this.promotions = promotions;
    });
  }
}
