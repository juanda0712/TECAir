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
  luggageForm: FormGroup;
  selectedSeats: any; //Asiento seleccionado
  seatNumber: number = 0; //Numero asignado al asiento seleccionado
  nuevaMaleta: any = {};
  weight: any;
  color: any;
  reservationID: any; //ID de reservacion <- Pagina anterior (check-in)
  passengerID: any;
  flight: any;
  flightInfo: any = {};
  reservation: any;
  reservationInfo: any = {};
  executionInfo: any = {};

  constructor(
    private FlightApi: ApiService<Flight>,
    private SuitcaseApi: ApiService<Suitcase>,
    private ReservationApi: ApiService<Reservation>,
    private ExecutionApi: ApiService<Execution>,
    private TicketApi: ApiService<Ticket>,
    private route: ActivatedRoute,

    private fb: FormBuilder
  ) {
    this.luggageForm = this.fb.group({
      weight: ['', Validators.required],
      color: ['', Validators.required],
    });
  }

  ngOnInit() {

    this.route.params.subscribe((params) => {
      this.reservationID = params['idReservation'];
      this.selectedSeats = params['seats'];
      this.seatNumber = params['numeroAsiento'];
    });

    this.ReservationApi.getSingleById(
      'Reservation',
      this.reservationID
    ).subscribe((data: any) => {
      this.reservationInfo = {
        idreservation: data.idreservation,
        idpassenger: data.idpassenger
      }
      this.ExecutionApi.getSingleById(
        'Execution/GetExecutionByID',
        data.idexecution
      ).subscribe((data2: any) => {
        this.executionInfo = {
          idexecution: data2.idexecution,
          numberFlight: data2.numberFlight,
          departureTime: data2.departureTime,
          price: data2.price,
          boardingDoor: data2.boardingDoor,
        }
        console.log(data2);
        this.FlightApi
          .getById('Flight', data2.numberFlight)
          .subscribe((data3: any) => {
            this.flightInfo = {
              origin: data3.origin,
              destination: data3.destination,
              flightNumber: data3.numberFlight
            }
            console.log(data3);
          });
      });
    });
  }

  addLuggage() {
    this.nuevaMaleta = {
      idpassenger: this.reservationInfo.idpassenger,
      idsuitCase: 0,
      weight: this.weight,
      color: this.color,
    };

    console.log('Nueva maleta: ', this.nuevaMaleta);

    this.SuitcaseApi.create('Suitcase', this.nuevaMaleta).subscribe(
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
    const ticket: Ticket = {
      idticket: 0,
      taxes: 0,
      totalAmount: 0,
      seatNumber: this.seatNumber,
      idexecution: this.executionInfo.idexecution,
      idpassenger: this.reservationInfo.idpassenger,
      iduser: undefined,
    };

    console.log(ticket);

    this.TicketApi.create('Ticket', ticket).subscribe(
      (data) => {
        console.log('Nuevo ticket creado:', data);
      },
      (error: any) => {
        console.error('Error al crear el nuevo ticket:', error);
      }
    );

    const docDefinition = {
      content: [
        { text: 'Ticket de abordaje', style: 'titulo', bold: true, fontSize: 30, color: '#1746a2' },
        { text: 'TECAir', style: 'subtitulo', bold: true, fontSize: 20, margins: 50 },
        `Número de reservacion: ${this.reservationID}`,
        `Número de vuelo: ${this.executionInfo.numberFlight}`,
        `Origen: ${this.flightInfo.origin}`,
        `Destino: ${this.flightInfo.destination}`,
        `Asiento seleccionado: ${this.selectedSeats}`,
        `Hora de salida: ${this.executionInfo.departureTime}`,
        `Puerta de abordaje: ${this.executionInfo.boardingDoor}`,
        `Precio: ${this.executionInfo.price}`,
      ],
    };

    pdfMake.createPdf(docDefinition).open();
  }
}
