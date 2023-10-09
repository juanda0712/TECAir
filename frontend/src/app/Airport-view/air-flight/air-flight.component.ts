import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { FlightCardComponent } from 'src/app/Components/Reusables/flight-card/flight-card.component';

@Component({
  selector: 'app-air-flight',
  templateUrl: './air-flight.component.html',
  styleUrls: ['./air-flight.component.css'],
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FlightCardComponent],
})
export class AirFlightComponent {

}
