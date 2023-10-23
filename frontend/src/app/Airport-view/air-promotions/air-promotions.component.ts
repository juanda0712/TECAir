import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/Services/api-service';
import { Execution, Promotion } from 'src/app/Interfaces/execution';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-air-promotions',
  templateUrl: './air-promotions.component.html',
})
export class AirPromotionsComponent {
  executionList: Execution[] = [];
  promotionList: Promotion[] = [];
  editMode = false;
  promotionForm: FormGroup;
  constructor(
    private promotionApi: ApiService<Promotion>,
    private executionApi: ApiService<Execution>,
    private fb: FormBuilder
  ) {
    this.promotionForm = this.fb.group({
      number: [0, Validators.nullValidator],
      idexecution: [0, Validators.required],
      period: ['', Validators.required],
      price: [0, Validators.required],
    });
  }

  ngOnInit() {
    this.executionApi.getAll('Execution').subscribe((data) => {
      this.executionList = data;
    });
    this.promotionApi.getAll('Promotion').subscribe((data) => {
      this.promotionList = data;
    });
  }

  editMovie(promotion: Promotion) {
    this.editMode = true;
    this.promotionForm.setValue({
      number: promotion.number,
      idexecution: promotion.idexecution,
      period: promotion.period,
      price: promotion.price,
    });
  }

  saveMovie() {
    if (this.promotionForm.valid) {
      const newPromotion: Promotion = this.promotionForm.value;

      if (this.editMode) {
        this.promotionApi
          .update('Promotion', newPromotion.number, newPromotion)
          .subscribe(
            (data: any) => {
              this.ngOnInit();
            },
            (error: any) => {
              console.error('Error al actualizar promocion:', error);
            }
          );
      } else {
        this.promotionApi.create('Promotion', newPromotion).subscribe(
          (data) => {
            this.ngOnInit();
          },
          (error: any) => {
            console.error('Error al crear promocion:', error);
          }
        );
      }

      this.createNew();
    }
  }

  deleteMovie(promotion: Promotion) {
    this.promotionApi.delete('Promotion', promotion.number).subscribe(
      () => {
        this.ngOnInit();
      },
      (error: any) => {
        console.error('Error al eliminar película:', error);
      }
    );
  }

  createNew() {
    this.editMode = false;
    this.promotionForm.reset();
  }
}
