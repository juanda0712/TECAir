import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { LoginRequest } from 'src/app/Interfaces/airport';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/Services/login-service';

@Component({
  selector: 'app-air-login',
  templateUrl: './air-login.component.html',
  styleUrls: ['./air-login.component.css'],
})
export class AirLoginComponent {
  loginForm: FormGroup;
  constructor(
    private api: ApiService<LoginRequest>,
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  login() {
    // Obtén las credenciales del formulario
    const credentials: LoginRequest = this.loginForm.value;

    // Realiza la solicitud de inicio de sesión al backend
    this.api.create('Login/EmployeeLogin', credentials).subscribe(
      (data: any) => {
        this.authService.login();
        sessionStorage.setItem('iduser', data.iduser);
        this.airHome();
      },
      (error: any) => {
        console.error('Error al iniciar sesión:', error);
      }
    );
  }

  airHome() {
    this.router.navigate(['/air-home']);
  }
}
