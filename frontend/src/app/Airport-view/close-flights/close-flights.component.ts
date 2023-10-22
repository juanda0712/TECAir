import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Execution } from 'src/app/Interfaces/execution';
import { NgSelectModule } from '@ng-select/ng-select';


@Component({
  selector: 'app-close-flights',
  templateUrl: './close-flights.component.html',
})
export class CloseFlightsComponent {
  changeStatus: Execution[] = [];
  idexecution: any;

  constructor(private api: ApiService<Execution>) { }

  update() {

    this.api.getSingleById(
      'Execution/GetExecutionByID',
      this.idexecution
    ).subscribe((execution: any) => {
      let newStatus: string;


      if (execution.status !== undefined && execution.status === 'open') {
        newStatus = 'closed';
      } else {
        newStatus = execution.status;
      }

      execution.status = newStatus;
      console.log(execution);

      // Llamada para actualizar el estatus
      this.api.update('Execution', this.idexecution, execution).subscribe(
        (updatedExecution: any) => {
          // Actualización exitosa
          console.log('Estatus actualizado:', updatedExecution);
        },
        (error: any) => {

          console.error('Error al actualizar el estatus:', error);
        }
      );
    })
  }
}
