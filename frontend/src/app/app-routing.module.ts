import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { DateComponent } from './Components/date/date.component';
import { FlightComponent } from './Components/flight/flight.component';
import { BookingComponent } from './Components/booking/booking.component';
import { LoginComponent } from './Components/login/login.component';
import { PromotionsComponent } from './Components/promotions/promotions.component';
import { ContactComponent } from './Components/contact/contact.component';
import { NewAccountComponent } from './Components/new-account/new-account.component';
import { AirLoginComponent } from './Airport-view/air-login/air-login.component';
import { AirNewAccountComponent } from './Airport-view/air-new-account/air-new-account.component';
import { AirFlightComponent } from './Airport-view/air-flight/air-flight.component';

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
  {
    path: 'app-new-account',
    component: NewAccountComponent,
    title: 'Cuenta',
  },
  {
    path: 'app-air-login',
    component: AirLoginComponent,
    title: 'AirSesion',
  },
  {
    path: 'app-air-new-account',
    component: AirNewAccountComponent,
    title: 'AirCuenta',
  },
  {
    path: 'app-air-flight',
    component: AirFlightComponent,
    title: 'AirVuelos',
  },




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
