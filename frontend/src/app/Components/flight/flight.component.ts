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
  selectedOrigin: any;
  selectedDestination: any;
  selectedDate: any;

  constructor(private api: ApiService<Flight>, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.selectedDate = params['selectedDate'];
      this.selectedOrigin = params['selectedOrigin'];
      this.selectedDestination = params['selectedDestination'];
    });
    this.api.getByThreeIds('Flight', this.selectedDate, this.selectedOrigin, this.selectedDestination).subscribe( //Servicio para obtener los vuelos por fecha, origen y destino
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