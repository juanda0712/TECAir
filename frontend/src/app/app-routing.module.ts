import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { DateComponent } from './Components/date/date.component';
import { FlightComponent } from './Components/flight/flight.component';
import { BookingComponent } from './Components/booking/booking.component';
import { LoginComponent } from './Components/login/login.component';
import { PromotionsComponent } from './Components/promotions/promotions.component';
import { ContactComponent } from './Components/contact/contact.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Inicio',
  },
  {
    path: 'date',
    component: DateComponent,
    title: 'Fechas'
  },
  {
    path: 'flights',
    component: FlightComponent,
    title: 'Vuelos'
  },
  {
    path: 'app-contact',
    component: ContactComponent,
    title: 'Contacto'
  },
  {
    path: 'booking',
    component: BookingComponent,
    title: 'Reservar'
  },
  {
    path: 'app-promotions',
    component: PromotionsComponent,
    title: 'Promos'
  },
  {
    path: 'app-login',
    component: LoginComponent,
    title: 'Sesion',
  },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
