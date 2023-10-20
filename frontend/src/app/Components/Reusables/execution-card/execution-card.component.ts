import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Execution } from 'src/app/Interfaces/execution';

@Component({
  selector: 'app-execution-card',
  templateUrl: './execution-card.component.html',
  styleUrls: ['./execution-card.component.css'],
})
export class ExecutionCardComponent {
  @Input() execution!: Execution;
  @Input() origin!: String;
  @Input() destination!: String;

  constructor(private router: Router) {}

  booking() {
    this.router.navigate(['/booking'], {
      queryParams: {
        idexecution: this.execution.idexecution,
      },
    });
  }
}
