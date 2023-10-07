import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { FlightCardComponent } from '../Reusables/flight-card/flight-card.component';

@Component({
  selector: 'flights',
  templateUrl: './flight.component.html',
  standalone: true,
  imports:[CommonModule, RouterOutlet, RouterLink, FlightCardComponent],
  styleUrls: ['../../../../src/styles.css']
})
export class FlightComponent {
}