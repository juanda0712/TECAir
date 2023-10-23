import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from 'src/app/Interfaces/airport';
import { User } from 'src/app/Interfaces/passenger';
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
  authenticated: boolean = false;
  userName?: String;

  constructor(
    private api: ApiService<Airport>,
    private userApi: ApiService<User>,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.homeForm = this.fb.group({
      origin: ['', Validators.required],
      destination: ['', Validators.required],
    });
  }

  ngOnInit() {
    const iduser = sessionStorage.getItem('iduser');
    const auth = sessionStorage.getItem('auth');
    this.api.getAll('Airport').subscribe(
      (locationsList: Airport[]) => {
        this.locationsList = locationsList;
      },
      (error: any) => {
        console.error('Error fetching locations:', error);
      }
    );
    if (iduser == undefined) {
      iduser == undefined;
    }
    if (iduser !== undefined && iduser !== null && auth === 'true') {
      this.authenticated = true;
      this.userApi.getSingleById('User', iduser).subscribe(
        (user: User) => {
          this.userName = user.fullname;
        },
        (error: any) => {
          console.error('Error fetching locations:', error);
        }
      );
    } else {
      this.authenticated = false;
      console.log('no existe usuario');
    }
  }

  selectDate() {
    this.router.navigate([
      '/date',
      this.selectedOrigin,
      this.selectedDestination,
    ]);
  }
}
