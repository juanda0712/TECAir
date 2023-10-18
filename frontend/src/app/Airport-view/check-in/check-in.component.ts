import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'air-home',
  templateUrl: './check-in.component.html',
  standalone: true,
  imports: [NgSelectModule, FormsModule, RouterOutlet, RouterLink, CommonModule],
  styleUrls: ['./check-in.component.css']
})
export class CheckInComponent {
  selectedSeats: string[] = [];

  cellColors: { [key: string]: string } = {};

  constructor(private router: Router) { }


  ngOnInit(): void {

    this.rows = Array.from({ length: this.numRows }, (_, i) => String.fromCharCode(65 + i));
    this.cols = Array.from({ length: this.numCols }, (_, i) => i + 1);
  }

  numRows: number = 20; // Número de filas predeterminado
  numCols: number = 6; // Número de columnas predeterminado

  rows: string[] = [];
  cols: number[] = [];

  cellClicked(row: string, col: number) {
    console.log(`Celda clickeada: Fila ${row}, Columna ${col}`);

    const cellKey = row + col;
    this.cellColors[cellKey] = this.cellColors[cellKey] === '#5f9cf7' ? '' : '#5f9cf7';


    //Guardar los asientos seleccionados
    const asiento = `${row}-${col}`;

    // Verificar si el asiento ya ha sido seleccionado visualmente
    const isSelected = this.cellColors[asiento] === '#5f9cf7';

    if (isSelected) {
      // Si el asiento está seleccionado visualmente, guardarlo
      if (!this.selectedSeats.includes(asiento)) {
        this.selectedSeats.push(asiento);
      }
    } else {
      // Si el asiento no está seleccionado visualmente, eliminarlo si existe
      const index = this.selectedSeats.indexOf(asiento);
      if (index !== -1) {
        this.selectedSeats.splice(index, 1);
      }
    }
  }

  luggage() {
    this.router.navigate(['/luggage'])
  }
}