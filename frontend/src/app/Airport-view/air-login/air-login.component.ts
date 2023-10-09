import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-air-login',
  templateUrl: './air-login.component.html',
  styleUrls: ['./air-login.component.css']
})
export class AirLoginComponent {
  constructor(private router: Router) { }
  newAccount() {
    this.router.navigate(['/app-air-new-account']);
  }

}
