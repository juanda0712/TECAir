import { NgModule } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Flight } from 'src/app/Interfaces/airport';
import { Component } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';


(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'luggage',
  templateUrl: './luggage.component.html',
  styleUrls: ['../../../../src/styles.css'],
})

export class LuggageComponent {

  constructor(
    private api: ApiService<Flight>, 
    private router: Router,  
    private route: ActivatedRoute) { }

  nuevaMaleta: any = {}; 
  maletas: any[] = [];
  flightID: any;
  flight: any;
  origin: any;
  destination: any;

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.flightID = params['flightID'];
    });

    this.api.getById('Flight', this.flightID).subscribe(
      (flight: Flight[]) => {
        this.flight = flight;
      },
      (error: any) => {
        console.error('Error fetching locations:', error);
      }
    );

    this.origin = this.flight.origin;
    this.destination = this.flight.destination;
  }

  addLuggage() {
    this.maletas.push(this.nuevaMaleta);
    this.nuevaMaleta = {};
  }

  generatePDF() {
    const docDefinition = {
      content: [
        { text: 'Tickete de abordaje', style: 'titulo' },
        { text: 'TECAir', style: 'subtitulo' },
        'Numero de vuelo: ${this.flightID}',
        'Origen: ${this.origin}',
        'Destino: ${this.destination}'
      ],
    };

    pdfMake.createPdf(docDefinition).open();
  }

}