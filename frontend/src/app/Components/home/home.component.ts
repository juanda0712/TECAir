import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';
@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['../../../../src/styles.css'],
})
export class HomeComponent implements OnInit {
  locationsList: Airport[] = [];
  selectedOption: any;
  homeForm: FormGroup;
  selectedOrigin: any;
  selectedDestination: any;

  constructor(
    private api: ApiService<Airport>,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.homeForm = this.fb.group({
      origin: ['', Validators.required],
      destination: ['', Validators.required],
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

  selectDate() {
    this.router.navigate([
      '/date',
      this.selectedOrigin,
      this.selectedDestination,
    ]);
  }
}
