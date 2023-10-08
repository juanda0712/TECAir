import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms'; 
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'booking',
  templateUrl: './booking.component.html',
  standalone: true,
  imports: [NgSelectModule, FormsModule, RouterOutlet, RouterLink],
  styleUrls: ['./booking.component.css']
})

export class BookingComponent {
  constructor(private router: Router) { }

  goBack() {
    this.router.navigate(['/flights']);
  }
}