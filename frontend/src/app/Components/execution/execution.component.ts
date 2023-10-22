import { Component } from '@angular/core';
import { ApiService } from 'src/app/Services/api-service';
import { Execution } from 'src/app/Interfaces/execution';
import { ActivatedRoute } from '@angular/router';
import { ExecutionCardComponent } from '../Reusables/execution-card/execution-card.component';

@Component({
  selector: 'app-execution',
  templateUrl: './execution.component.html',
  styleUrls: ['./execution.component.css'],
})
export class ExecutionComponent {
  executions: Execution[] = [];
  selectedOrigin: any;
  selectedDestination: any;
  selectedDate: any;

  constructor(
    private api: ApiService<Execution>,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.selectedDate = params['date'];
      this.selectedOrigin = params['origin'];
      this.selectedDestination = params['destination'];
    });
    this.api
      .getByCriteria(
        'Execution',
        this.selectedDate,
        this.selectedOrigin,
        this.selectedDestination
      )
      .subscribe(
        (executions: Execution[]) => {
          console.log(executions);
          this.executions = executions;
        },
        (error: any) => {
          console.error('Error fetching flights:', error);
        }
      );
  }
}
