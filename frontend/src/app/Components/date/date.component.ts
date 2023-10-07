import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'date',
  templateUrl: './date.component.html',
  standalone: true,
  imports:[CommonModule, RouterOutlet, RouterLink],
  styleUrls: ['../../../../src/styles.css']
})
export class DateComponent {
  constructor(private router: Router) { }

  months: { value: number, name: string }[] = [
    { value: 1, name: 'Enero' },
    { value: 2, name: 'Febrero' },
    { value: 3, name: 'Marzo' },
    { value: 4, name: 'Abril' },
    { value: 5, name: 'Mayo' },
    { value: 6, name: 'Junio' },
    { value: 7, name: 'Julio' },
    { value: 8, name: 'Agosto' },
    { value: 9, name: 'Septiembre' },
    { value: 10, name: 'Octubre' },
    { value: 11, name: 'Noviembre' },
    { value: 12, name: 'Diciembre' }
  ];

  days: number[] = Array.from({ length: 31 }, (_, i) => i + 1);
  years: number[] = [2023, 2024, 2025];

  seeFlights() {
    this.router.navigate(['/flights']);
  }

  goBack() {
    this.router.navigate(['/']);
  }
  
}