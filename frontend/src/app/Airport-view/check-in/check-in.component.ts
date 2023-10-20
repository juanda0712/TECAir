import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';

@Component({
  selector: 'check-in',
  templateUrl: './check-in.component.html',
  standalone: true,
  imports: [NgSelectModule, FormsModule, RouterOutlet, RouterLink, CommonModule],
  styleUrls: ['./check-in.component.css']
})
export class CheckInComponent {

  selectedSeats: string[] = [];
  cellColors: { [key: string]: string } = {};
  flightID: any;
  flight: any;

  constructor(private router: Router, private api: ApiService<Flight>) { }


  ngOnInit(): void {

    this.rows = Array.from({ length: this.numRows }, (_, i) => String.fromCharCode(65 + i));
    this.cols = Array.from({ length: this.numCols }, (_, i) => i + 1);
  }

  numRows: number = 20; // Número de filas 
  numCols: number = 6; // Número de columnas 

  rows: string[] = [];
  cols: number[] = [];
  cellClicked(row: string, col: number) {
    const cellKey = row + col;
    const isAlreadySelected = this.selectedSeats.includes(cellKey);
  
    // Deseleccionar el asiento si ya estaba seleccionado
    if (isAlreadySelected) {
      this.cellColors[cellKey] = '';
      const index = this.selectedSeats.indexOf(cellKey);
      if (index !== -1) {
        this.selectedSeats.splice(index, 1);
      }
      console.log(`Asiento (${row}-${col}) ha sido deseleccionado`);
    } else {
      // Deseleccionar todos los asientos previamente seleccionados
      this.selectedSeats.forEach((seat) => {
        this.cellColors[seat] = '';
      });
      this.selectedSeats = []; // Limpiar la lista de asientos seleccionados
  
      // Marcar el asiento actual como seleccionado
      this.cellColors[cellKey] = '#5f9cf7';
      this.selectedSeats.push(cellKey);
      console.log(`Asiento (${row}-${col}) ha sido seleccionado`);
    }
  }
  

  luggage() {

    /**this.api.getById('Flight', this.flightID).subscribe(
      (flight: Flight[]) => {
        if (flight && flight.length > 0) {
          this.flight = flight;
          let seats = this.selectedSeats.join(', ');
          this.router.navigate(['/luggage', this.flightID, seats]);
        } else {

        }
      },
      (error: any) => {
        console.error('Error fetching flight:', error);
      }
    );
  }**/

    let seats = this.selectedSeats.join(', ');
    this.router.navigate(['/luggage', this.flightID, seats]);
  }
}