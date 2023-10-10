import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms'; 
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'luggage',
  templateUrl: './luggage.component.html',
  standalone: true,
  imports: [NgSelectModule, FormsModule, RouterOutlet, RouterLink],
  styleUrls: ['./luggage.component.css']
})
export class LuggageComponent {

    constructor(private router: Router) { }

    nuevaMaleta: any = {}; // Objeto para almacenar los datos de la nueva maleta
    maletas: any[] = []; // Lista de maletas

    addLuggage() {
        // Agregar la nueva maleta a la lista de maletas
        this.maletas.push(this.nuevaMaleta);

        // Limpiar los campos del formulario después de agregar la maleta
        this.nuevaMaleta = {};

    }

}