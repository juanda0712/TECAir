import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from 'src/app/Interfaces/airport';
import { Flight } from 'src/app/Interfaces/airport';
import { Layover } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';

@Component({
  selector: 'flight-management',
  templateUrl: './flight-management.component.html',
  styleUrls: ['./flight-management.component.css']
})
export class FlightManagementComponent implements OnInit {
  locationsList: Airport[] = [];
  selectedOption: any;
  addFlightForm: FormGroup;
  selectedOrigin: any;
  selectedDestination: any;
  selectedLayover: string = '';
  layovers: any[] = [];
  flight: any;
  layoverOrigin: any;


  constructor(
    private api: ApiService<Airport>,
    private FlightApi: ApiService<Flight>,
    private LayoverApi: ApiService<Layover>,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.addFlightForm = this.fb.group({
      origin: ['', Validators.required],
      destination: ['', Validators.required],
      layover: [''],
      layoverOrigin: ['']
    });
  }

  ngOnInit() {
    this.api.getAll('Airport').subscribe(
      (locationsList: Airport[]) => {
        this.locationsList = locationsList;
      },
      (error: any) => {
        console.error('Error fetching locations:', error);
      }
    );
  }

  addFlight() {
    console.log(this.layovers);

    this.flight = {
      number: 0,
      origin: this.selectedOrigin,
      destination: this.selectedDestination
    };

    this.FlightApi.create('Flight', this.flight).subscribe(
      (data) => {
        console.log('Nuevo vuelo creada:', data);
      },
      (error: any) => {
        console.error('Error al crear nueva vuelo:', error);
      }
    );

    this.layovers.forEach((element: { origin: any; }) => {

      console.log(element);

      this.LayoverApi.create('Layover', element).subscribe(
        (data) => {
          console.log('Nueva escala creada:', data);
        },
        (error: any) => {
          console.error('Error al crear nueva escala:', error);
        }
      );

    });

  }

  addLayover() {
    if (this.selectedLayover.trim() !== '') {

      const layover: Layover = {
        idlayover: 0,
        numberFlight: this.flight.number,
        origin: this.layoverOrigin,
        destination: this.selectedLayover
      };

      this.layovers.push(layover);
      this.selectedLayover = '';
      this.layoverOrigin = '';
    }
  }
}