import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-air-booking',
  templateUrl: './air-booking.component.html',
  styleUrls: ['./air-booking.component.css']
})
export class AirBookingComponent {
  constructor(private router: Router) { }

  goBack() {
    this.router.navigate(['/flights']);
  }

}
