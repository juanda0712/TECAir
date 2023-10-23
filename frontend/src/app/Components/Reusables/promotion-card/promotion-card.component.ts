import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Flight } from 'src/app/Interfaces/airport';
import { Execution, Promotion } from 'src/app/Interfaces/execution';
import { ApiService } from 'src/app/Services/api-service';

@Component({
  selector: 'app-promotion-card',
  templateUrl: './promotion-card.component.html',
  styleUrls: ['./promotion-card.component.css'],
})
export class PromotionCardComponent {
  @Input() promotion!: Promotion;
  execution: Execution = {};
  flight: Flight = {};

  constructor(
    private router: Router,
    private executionApi: ApiService<Execution>,
    private flightApi: ApiService<Flight>
  ) {}

  ngOnInit() {
    this.executionApi
      .getSingleById('Execution/GetExecutionByID', this.promotion.idexecution)
      .subscribe((execution: Execution) => {
        this.execution = execution;
        this.flightApi
          .getSingleById('Flight', execution.numberFlight)
          .subscribe((flight: Flight) => {
            this.flight = flight;
          });
      });
  }

  booking() {
    this.router.navigate(['/booking'], {
      queryParams: {
        idexecution: this.promotion.idexecution,
      },
    });
  }
}
