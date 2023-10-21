import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from '@angular/forms';
import { RouterOutlet, RouterLink, ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Passenger, User } from 'src/app/Interfaces/passenger';
import { ApiService } from 'src/app/Services/api-service';
import { Reservation } from 'src/app/Interfaces/execution';

@Component({
  selector: 'booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
})
export class BookingComponent {
  bookingForm: FormGroup;
  idexecution?: String;
  visibleDialog = false;
  authenticated: boolean = false;
  user: User = {};

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private passengerApi: ApiService<Passenger>,
    private reservationApi: ApiService<Reservation>,
    private userApi: ApiService<User>
  ) {
    this.bookingForm = this.fb.group({
      idpassenger: [0],
      fullname: ['', Validators.required],
      phonenumber: [0, Validators.required],
    });
  }

  ngOnInit() {
    const iduser = sessionStorage.getItem('iduser');
    this.route.queryParams.subscribe((params) => {
      this.idexecution = params['idexecution'];
    });
    if (iduser) {
      this.authenticated = true;
      this.userApi.getSingleById('User', iduser).subscribe(
        (user: User) => {
          this.user = user;
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

  reserved() {
    if (this.authenticated) {
      const reservation: Reservation = {
        idreservation: 0,
        idpassenger: this.user.idpassenger,
        idexecution: Number(this.idexecution),
      };
      this.reservationApi.create('Reservation', reservation).subscribe(
        (data) => {
          this.router.navigate(['/']);
        },
        (error: any) => {
          console.error('Error al crear reservacion:', error);
        }
      );
    } else {
      const newPassenger: Passenger = this.bookingForm.value;
      const reservation: Reservation = {
        idreservation: 0,
        idpassenger: 0,
        idexecution: Number(this.idexecution),
      };
      this.passengerApi.create('Passenger', newPassenger).subscribe(
        (data) => {
          reservation.idpassenger = data.idpassenger;
          this.reservationApi.create('Reservation', reservation).subscribe(
            (data) => {
              this.router.navigate(['/']);
            },
            (error: any) => {
              console.error('Error al crear reservacion:', error);
            }
          );
        },
        (error: any) => {
          console.error('Error al crear pasajero:', error);
        }
      );
    }
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
