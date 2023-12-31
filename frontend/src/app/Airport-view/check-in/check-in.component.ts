import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';
import { Seat } from 'src/app/Interfaces/execution';
import { Reservation } from 'src/app/Interfaces/execution';
import { Execution } from 'src/app/Interfaces/execution';

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
  reservationID: any;
  flight: any;
  reservation: any;

  constructor(
    private router: Router,
    private api: ApiService<Flight>,
    private ReservationApi: ApiService<Reservation>,
    private ExecutionApi: ApiService<Execution>,
    private SeatApi: ApiService<Seat>,) {
  }

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

    //Obtener la ejecucion con el ID de la reservacion
    this.ReservationApi.getSingleById('Reservation', this.reservationID).subscribe(
      (reservation: Reservation) => {
        this.reservation = reservation;

        this.ExecutionApi.getSingleById(
          'Execution/GetExecutionByID',
          reservation.idexecution
        ).subscribe((execution: any) => {

          if (execution.status == 'open') {

            const [letra, numeroStr] = this.selectedSeats[0];
            const valorLetra = letra.charCodeAt(0) - 'A'.charCodeAt(0) + 1;
            const valorNumero = parseInt(numeroStr, 10);
            const numeroAsiento = (valorLetra - 1) * this.numCols + valorNumero;

            const asiento: Seat = {
              seatNumber: numeroAsiento,
              idexecution: this.reservation.idexecution
            };

            console.log(asiento);

            //Creación del asiento
            this.SeatApi.create('Seat', asiento).subscribe(
              (data) => {
                console.log('Nuevo asiento creado:', data);
                let seats = this.selectedSeats.join(', ');
                this.router.navigate(['/luggage', this.reservationID, seats, numeroAsiento]);
              },
              (error: any) => {
                console.error('Error al crear el nuevo asiento:', error);
              }
            );
            (error: any) => {
              console.error('Error fetching reservation:', error);
            }
          }
          else {
            console.log("El vuelo seleccionado no se encuentra abierto ")
          }
        }
        );
      }
    )
  }


}