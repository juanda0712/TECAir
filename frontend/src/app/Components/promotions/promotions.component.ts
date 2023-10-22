import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { FlightCardComponent } from '../Reusables/flight-card/flight-card.component';


@Component({
  selector: 'app-promotions',
  templateUrl: './promotions.component.html',
  styleUrls: ['./promotions.component.css'],
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FlightCardComponent],
})
export class PromotionsComponent {

}
