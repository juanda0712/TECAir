import { Component } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  standalone: true,
  imports: [NgSelectModule, FormsModule],
  styleUrls: ['../../../../src/styles.css']
})
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

}
