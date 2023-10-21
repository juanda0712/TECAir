import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';
import { Component } from '@angular/core';
import { Suitcase } from 'src/app/Interfaces/passenger';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Execution, Reservation, Ticket } from 'src/app/Interfaces/execution';
import { forkJoin, switchMap } from 'rxjs';


(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'luggage',
  templateUrl: './luggage.component.html',
  styleUrls: ['./luggage.component.css'],
})

export class LuggageComponent {

  nuevaMaleta: any = {}; 
  maletas: any[] = [];
  weight: any;
  color: any;
  reservationID: any; //ID de reservacion <- Pagina anterior (check-in)
  flight: any; 
  origin: any; //Origen del vuelo <- Flight
  destination: any; //Destino del vuelo <-Flight
  luggageForm: FormGroup; 
  selectedSeats: any; //Asiento seleccionado
  seatNumber: any; //Numero asignado al asiento seleccionado
  executionID: any; //ID de la ejecucion <- Reservacion
  passengerID: any; //ID del pasajero <- Reservacion
  reservation: any;  
  executionInfo: any = {};
  docDefinition: any;


  constructor(
    private api: ApiService<Flight>, 
    private SuitcaseApi: ApiService<Suitcase>,
    private ReservationApi: ApiService<Reservation>,
    private ExecutionApi: ApiService<Execution>,
    private TicketApi: ApiService<Ticket>, 
    private route: ActivatedRoute,
    

    private fb: FormBuilder) {
      
    this.luggageForm = this.fb.group({
      weight: ['', Validators.required],
      color: ['', Validators.required]
    });
    }


    ngOnInit() {
      this.route.params.pipe(
        switchMap((params) => {
          this.reservationID = params['flightID'];
          this.selectedSeats = params['seats'];
          this.seatNumber = params['numeroAsiento'];
          return this.ReservationApi.getSingleById('Reservation', this.reservationID);
        }),
        switchMap((reservation: Reservation) => {
          this.reservation = reservation;
          this.executionID = reservation.idexecution;
          this.passengerID = reservation.idpassenger;
          console.log("ID ejecucion:", this.executionID);
          return this.ExecutionApi.getSingleById('Execution', this.executionID);
        }),
        switchMap((execution: Execution) => {
          this.executionInfo = {
            idexecution: this.executionID,
            numberFlight: execution.numberFlight,
            departureTime: execution.departureTime,
            price: execution.price,
            boardingDoor: execution.boardingDoor,
          };
          console.log("Execution INFO: ",this.executionInfo);
          return this.api.getById('Flight', this.executionID);
        }),
        switchMap((flight: Flight[]) => {
          this.flight = flight;
          this.origin = this.flight.origin;
          this.destination = this.flight.destination;

          this.docDefinition = {
            content: [
              { text: 'Ticket de abordaje', style: 'titulo', bold: true, fontSize: 30, color: '#1746a2'},
              { text: 'TECAir', style: 'subtitulo', bold: true, fontSize: 20, margins: 50 },
              `Número de reservacion: ${this.reservationID}`,
              `Número de vuelo: ${this.executionInfo.numberFlight}`,
              `Origen: ${this.origin}`,
              `Destino: ${this.destination}`,
              `Asiento seleccionado: ${this.selectedSeats}`,
              `Hora de salida: ${this.executionInfo.departureTime}`,
              `Puerta de abordaje: ${this.executionInfo.boardingDoor}`,
              `Precio: ${this.executionInfo.price}`,
            ],
          };
          return [];
        })
        ).subscribe(() => {}, (error: any) => {
        console.error('Error:', error);
      });
    }

  addLuggage() {

    this.nuevaMaleta = {
      owner: this.passengerID,
      number: 0,
      weight: this.weight,
      color: this.color
    }
    this.maletas.push(this.nuevaMaleta);
    console.log("Nueva maleta: ", this.nuevaMaleta)

    this.SuitcaseApi.create('Seat', this.nuevaMaleta).subscribe(
      (data) => {
        console.log('Nueva maleta creada:', data);

      },
      (error: any) => {
        console.error('Error al crear el nueva maleta:', error);
      }
    );

    this.nuevaMaleta = {};     
  }

  generateTicket() {
    console.log(this.maletas);

    const ticket: Ticket = {
      idticket: 0,
      taxes: 0,
      totalAmount: 0,
      seatNumber: this.seatNumber,
      idexecution: this.executionID,
      idpassenger: this.passengerID,
      iduser: 0
    };

    this.TicketApi.create('Ticket', ticket).subscribe(
      (data) => {
        console.log('Nuevo ticket creado:', data);
      },
      (error: any) => {
        console.error('Error al crear el nuevo ticket:', error);
      }
    );
  

    pdfMake.createPdf(this.docDefinition).open();
  }

}