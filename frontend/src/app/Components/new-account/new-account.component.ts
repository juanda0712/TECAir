import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { Passenger, Student, User } from 'src/app/Interfaces/passenger';
import { ApiService } from 'src/app/Services/api-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css'],
})
export class NewAccountComponent {
  registerForm: FormGroup;
  universityName?: string;
  universityCard?: number;
  isStudent: boolean = false;
  constructor(
    private userApi: ApiService<User>,
    private passengerApi: ApiService<Passenger>,
    private studentApi: ApiService<Student>,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.registerForm = this.fb.group({
      iduser: [0, Validators.required],
      fullname: ['', Validators.required],
      phonenumber: [0, Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      idpassenger: [0, Validators.required],
    });
  }

  submit() {
    //const nombrePelicula = this.branchMovieForm.get('nombrePelicula')?.value;
    const newPassenger = new Passenger();
    newPassenger.fullname = this.registerForm.get('fullname')?.value;
    newPassenger.phonenumber = this.registerForm.get('phonenumber')?.value;

    this.passengerApi.create('Passenger', newPassenger).subscribe((data) => {
      this.registerForm.patchValue({ idpassenger: data.idpassenger });
      const newUser: User = this.registerForm.value;
      this.userApi.create('User', newUser).subscribe((data) => {
        if (this.isStudent) {
          const newStudent = new Student();
          newStudent.iduser = 0;
          newStudent.universityCard = this.universityCard;
          newStudent.universityName = this.universityName;
          newStudent.iduser = data.iduser;
          newStudent.miles = 0;
          this.studentApi.create('Student', newStudent).subscribe((data) => {
            this.router.navigate(['/app-login']);
          });
        } else {
          this.router.navigate(['/app-login']);
        }
      });
    });
  }

  toggleEstudiante() {
    this.isStudent = !this.isStudent;
    console.log(this.isStudent);
  }
}
