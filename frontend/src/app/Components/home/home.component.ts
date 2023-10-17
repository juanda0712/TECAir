import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { Airport } from 'src/app/Interfaces/airport';
import { ApiService } from 'src/app/Services/api-service';
@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  standalone: true,
  imports:[NgSelectModule, FormsModule],
  styleUrls: ['../../../../src/styles.css']
})
export class HomeComponent implements OnInit {

  locationsList: Airport[] = [];
  origen: Airport[] = [];
  destino: Airport[] = [];
  selectedOption: any;

  constructor(private api: ApiService<Airport>, private router: Router) { }

  ngOnInit() {
    this.api.getAll('Airport').subscribe(
      (locationsList: Airport[]) => {
        this.locationsList = locationsList;
        this.origen = [...locationsList]; // Copia de locationsList a origen
        this.destino = [...locationsList]; // Copia de locationsList a destino
      },
      (error: any) => {
        console.error('Error fetching locations:', error);
      }
    );
  }

  selectDate() {
    this.router.navigate(['/date']);
  }
}
