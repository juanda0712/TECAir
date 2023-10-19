import { Component, Input } from '@angular/core';
import { Execution } from 'src/app/Interfaces/execution';

@Component({
  selector: 'app-execution-card',
  templateUrl: './execution-card.component.html',
  styleUrls: ['./execution-card.component.css'],
})
export class ExecutionCardComponent {
  @Input() execution!: Execution;
}
