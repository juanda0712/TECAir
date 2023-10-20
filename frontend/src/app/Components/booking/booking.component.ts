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
import { Passenger } from 'src/app/Interfaces/passenger';
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

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private passengerApi: ApiService<Passenger>,
    private reservationApi: ApiService<Reservation>
  ) {
    this.bookingForm = this.fb.group({
      idpassenger: [0],
      fullname: ['', Validators.required],
      phonenumber: [0, Validators.required],
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.idexecution = params['idexecution'];
    });
  }

  reserved() {
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

  goBack() {
    this.router.navigate(['/']);
  }
}
