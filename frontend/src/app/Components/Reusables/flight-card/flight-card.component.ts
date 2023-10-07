import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-flight-card',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './flight-card.component.html',
  styleUrls: ['./flight-card.component.css']
})
export class FlightCardComponent {

}