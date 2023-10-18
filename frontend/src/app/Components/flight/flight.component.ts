import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { FlightCardComponent } from '../Reusables/flight-card/flight-card.component';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'flights',
  templateUrl: './flight.component.html',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FlightCardComponent],
  styleUrls: ['../../../../src/styles.css']
})
export class FlightComponent {
  flights: Flight[] = [];
  selectedDate: any;

  constructor(private api: ApiService<Flight>, private route: ActivatedRoute) { }

  ngOnInit() {
    this.selectedDate = this.route.snapshot.params['selectedDate'];
    this.api.getAll('Flight').subscribe( //Servicio para obtener los vuelos por fecha, origen y destino
      (flights: Flight[]) => {
        console.log(flights);
        this.flights = flights;
      },
      (error: any) => {
        console.error('Error fetching flights:', error);
      }
    );
  }
}