import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { CarouselModule } from 'ngx-bootstrap/carousel';

@Component({
  selector: 'air-home',
  templateUrl: './air-home.component.html',
  standalone: true,
  imports: [
    NgSelectModule,
    FormsModule,
    RouterOutlet,
    RouterLink,
    CarouselModule,
  ],
  styleUrls: ['./air-home.css'],
})
export class AirHomeComponent {
  constructor(private router: Router) {}

  search() {
    this.router.navigate(['/']);
  }

  checkIn() {
    this.router.navigate(['/check-in']);
  }

  manageFlights() {
    this.router.navigate(['/flight-management']);
  }

  openFlights() {
    this.router.navigate(['/app-opening-flights']);
  }

  closeFlights() {
    this.router.navigate(['/app-close-flights']);
  }
  promotion() {
    this.router.navigate(['/app-air-promotions']);
  }
}
