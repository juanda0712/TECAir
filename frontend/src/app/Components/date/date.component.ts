import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'date',
  templateUrl: './date.component.html',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FormsModule],
  styleUrls: ['../../../../src/styles.css']
})
export class DateComponent {
  constructor(private router: Router) { }

  selectedDay: any;
  selectedMonth: any;
  selectedYear: any;
  selectedDate: any;


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
    this.selectedDate = new Date(this.selectedYear, this.selectedMonth, this.selectedDay);
    this.router.navigate(['/flights'], this.selectedDate);
    console.log(this.selectedDay, this.selectedMonth, this.selectedYear)

  }

  goBack() {
    this.router.navigate(['/']);
  }

}