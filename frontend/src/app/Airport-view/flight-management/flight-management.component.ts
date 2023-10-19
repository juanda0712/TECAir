import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';

@Component({
  selector: 'flight-management',
  templateUrl: './flight-management.component.html',
  styleUrls: ['./flight-management.component.css']
})
export class FlightManagementComponent implements OnInit{
  locationsList: Airport[] = [];
  selectedOption: any;
  addFlightForm: FormGroup;
  selectedOrigin: any;
  selectedDestination: any;
  selectedLayover: string = '';
  layovers: string[] = [];

  constructor(
    private api: ApiService<Airport>,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.addFlightForm = this.fb.group({
      origin: ['', Validators.required],
      destination: ['', Validators.required],
      layover: ['']
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

  }

  addLayover() {
    if (this.selectedLayover.trim() !== '') {
      this.layovers.push(this.selectedLayover);
      this.selectedLayover = '';
    }
  }
}