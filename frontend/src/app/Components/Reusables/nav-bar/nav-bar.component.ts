import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  constructor(private router: Router) { }
  loginUser() {
    this.router.navigate(['/app-login']);
  }
  promotions() {
    this.router.navigate(['/app-promotions']);
  }
  contact() {
    this.router.navigate(['/app-contact']);
  }

  goBack() {
    this.router.navigate(['/']);
  }

}
