import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/Services/api-service';
import { LoginRequest } from 'src/app/Interfaces/airport';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private api: ApiService<LoginRequest>,
    private router: Router,
    private fb: FormBuilder
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
    this.api.create('Login/UserLogin', credentials).subscribe(
      (data: any) => {
        sessionStorage.setItem('iduser', data.iduser);
        sessionStorage.setItem('auth', 'true');
        this.home();
      },
      (error: any) => {
        console.error('Error al iniciar sesión:', error);
      }
    );
  }

  newAccount() {
    this.router.navigate(['/app-new-account']);
  }

  home() {
    this.router.navigate(['/']);
  }
}
