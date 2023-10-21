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

    private fb: FormBuilder
  ) {
    this.luggageForm = this.fb.group({
      weight: ['', Validators.required],
      color: ['', Validators.required],
    });
  }

  ngOnInit() {
    // this.reservationID = this.route.params['flightID'];
    this.route.params.subscribe((params) => {
      this.reservationID = params['idReservation'];
    });
    this.ReservationApi.getSingleById(
      'Reservation',
      this.reservationID
    ).subscribe((data: any) => {
      this.executionID = data.executionID;
      this.ExecutionApi.getSingleById(
        'Execution/GetExecutionByID',
        data.idexecution
      ).subscribe((data2: any) => {
        console.log(data2);
        this.api
          .getById('Flight', data2.numberFlight)
          .subscribe((data3: any) => {
            console.log(data3);
          });
      });
    });
  }

  addLuggage() {
    this.nuevaMaleta = {
      owner: this.passengerID,
      number: 0,
      weight: this.weight,
      color: this.color,
    };
    this.maletas.push(this.nuevaMaleta);
    console.log('Nueva maleta: ', this.nuevaMaleta);

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
      iduser: 0,
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
