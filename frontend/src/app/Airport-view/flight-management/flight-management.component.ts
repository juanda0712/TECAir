import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from 'src/app/Interfaces/airport';
import { Flight } from 'src/app/Interfaces/airport';
import { Layover } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';
import { switchMap } from 'rxjs/operators';

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
  flightID: any;


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

    this.FlightApi.create('Flight', this.flight)
      .pipe(
        switchMap((flightData) => {
          console.log('Nuevo vuelo creado:', flightData);
          this.flightID = flightData.number;
          // Crear escalas después de crear el vuelo
          const layoverPromises = this.layovers.map((element: Layover) => {
            return this.LayoverApi.create('Layover', element).toPromise();
          });

          return Promise.all(layoverPromises);
        })
      )
      .subscribe(
        (layoverResults) => {
          console.log('Escalas creadas:', layoverResults);
        },
        (error: any) => {
          console.error('Error al crear vuelo o escalas:', error);
        }
      );
  }

  addLayover() {
    if (this.selectedLayover.trim() !== '') {

      console.log("FNum: ", this.flight.number);

      const layover: Layover = {
        idlayover: 0,
        numberFlight: this.flightID,
        origin: this.layoverOrigin,
        destination: this.selectedLayover
      };

      console.log("Layover: ", layover);

      // Realizar la solicitud POST para crear la escala
      this.LayoverApi.create('Layover', layover).subscribe(
        (layoverData) => {
          console.log('Nueva escala creada:', layoverData);
        },
        (error: any) => {
          console.error('Error al crear nueva escala:', error);
        }
      );

      this.selectedLayover = '';
      this.layoverOrigin = '';
    }
  }


}