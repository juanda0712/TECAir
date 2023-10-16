import { Component, OnInit } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { RouterOutlet, RouterLink } from '@angular/router';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { Airport } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
})
export class HomeComponent {
  branchList: string[] = [];
  selectedOrigen: string = ''; // Variable para almacenar la zona de origen
  selectedDestino: string = ''; // Variable para almacenar la zona de destino

  constructor(private api: ApiService<Airport>) { }

  ngOnInit() {
    this.api.getAll('Airport').subscribe(
      (airportList: Airport[]) => {
        this.branchList = airportList.map((airport) => airport.City || '');
      },
      (error: any) => {
        console.error('Error fetching branch:', error);
      }
    );
  }

  onOrigenChange(event: any) {
    this.selectedOrigen = event.target.value;
  }

  onDestinoChange(event: any) {
    this.selectedDestino = event.target.value;
  }

  continuar() {
    // Lógica para continuar con las selecciones de origen y destino
    console.log('Origen seleccionado:', this.selectedOrigen);
    console.log('Destino seleccionado:', this.selectedDestino);
  }
}




/*
export class HomeComponent {
    origen = [
        { value: 'opcion1', label: 'Opción 1' },
        { value: 'opcion2', label: 'Opción 2' },
        { value: 'opcion3', label: 'Opción 3' },
      ];

    destino = [
        { value: 'opcion1', label: 'Opción 1' },
        { value: 'opcion2', label: 'Opción 2' },
        { value: 'opcion3', label: 'Opción 3' },
    ]
    
    selectedOption: any; // Variable para almacenar la opción seleccionada

    constructor(private router: Router) { }

    selectDate() {
      this.router.navigate(['/date']);
    }

}
*/