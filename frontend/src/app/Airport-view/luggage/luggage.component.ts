import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';
import { Component } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Alignment } from 'pdfmake/interfaces';


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
  flightID: any;
  flight: any;
  origin: any;
  destination: any;
  luggageForm: FormGroup;

  constructor(
    private api: ApiService<Flight>, 
    private router: Router,  
    private route: ActivatedRoute,
    private fb: FormBuilder) {
      
    this.luggageForm = this.fb.group({
      owner: [''],
      number: [''],
      weight: [''],
      color: ['']
    });

    }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.flightID = params['flightID'];
    });

    this.api.getById('Flight', this.flightID).subscribe(
      (flight: Flight[]) => {
        this.flight = flight;
        this.origin = this.flight.origin;
        this.destination = this.flight.destination;
      },
      (error: any) => {
        console.error('Error fetching locations:', error);
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
    this.nuevaMaleta = {}; 

    console.log("maletas", this.maletas)
  }

  generatePDF() {
    console.log(this.maletas);

    const docDefinition = {
      content: [
        { text: 'Ticket de abordaje', style: 'titulo', bold: true, fontSize: 30, color: '#1746a2'},
        { text: 'TECAir', style: 'subtitulo', bold: true, fontSize: 20, margins: 50 },
        `Número de vuelo: ${this.flightID}`,
        `Origen: ${this.origin}`,
        `Destino: ${this.destination}`
      ], 
    };
  

    pdfMake.createPdf(docDefinition).open();
  }

}