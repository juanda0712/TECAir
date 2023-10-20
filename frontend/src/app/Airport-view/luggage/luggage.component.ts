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


(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'luggage',
  templateUrl: './luggage.component.html',
  styleUrls: ['./luggage.component.css'],
})

export class LuggageComponent {

  nuevaMaleta: any = {}; 
  maletas: any[] = [];
  owner: any;
  number: any;
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


  constructor(
    private api: ApiService<Flight>, 
    private SuitcaseApi: ApiService<Suitcase>,
    private ReservationApi: ApiService<Reservation>,
    private ExecutionApi: ApiService<Execution>,
    private TicketApi: ApiService<Ticket>,
    private router: Router,  
    private route: ActivatedRoute,
    private fb: FormBuilder) {
      
    this.luggageForm = this.fb.group({
      owner: ['', Validators.required],
      number: ['', Validators.required],
      weight: ['', Validators.required],
      color: ['', Validators.required]
    });

    }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.reservationID = params['flightID'];
      this.selectedSeats = params['seats'];
      this.seatNumber = params['numeroAsiento']
    });


    this.api.getById('Flight', this.reservationID).subscribe(
      (flight: Flight[]) => {
        this.flight = flight;
        this.origin = this.flight.origin;
        this.destination = this.flight.destination;
      },
      (error: any) => {
        console.error('Error fetching flight:', error);
      }
    );

    //Obtener la ejecucion con el ID de la reservacion
    this.ReservationApi.getSingleById('Reservation', this.reservationID).subscribe(
      (reservation: Reservation) => {
        this.reservation = reservation;
        this.executionID = reservation.idexecution;
        this.passengerID = reservation.idpassenger;
      }
    );

    this.ExecutionApi.getSingleById('Execution', this.executionID).subscribe(
      (execution: Execution) => {
        this.executionInfo = {
          idexecution: this.executionID,
          numberFlight: execution.numberFlight,
          departureTime: execution.departureTime,
          price: execution.price,
          boardingDoor: execution.boardingDoor
        }
      }
    );
  }

  addLuggage() {

    this.nuevaMaleta = {
      owner: this.owner,
      number: this.number,
      weight: this.weight,
      color: this.color
    }
    this.maletas.push(this.nuevaMaleta);
    console.log("Se agrego la maleta: ", this.nuevaMaleta)

    this.SuitcaseApi.create('Seat', this.nuevaMaleta).subscribe(
      (data) => {
        console.log('Nuevo asiento creado:', data);
      },
      (error: any) => {
        console.error('Error al crear el nuevo asiento:', error);
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

    this.TicketApi.create('Seat', ticket).subscribe(
      (data) => {
        console.log('Nuevo ticket creado:', data);
      },
      (error: any) => {
        console.error('Error al crear el nuevo ticket:', error);
      }
    );

    const docDefinition = {
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
  

    pdfMake.createPdf(docDefinition).open();
  }

}