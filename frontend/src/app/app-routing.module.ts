import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { DateComponent } from './Components/date/date.component';
import { BookingComponent } from './Components/booking/booking.component';
import { LoginComponent } from './Components/login/login.component';
import { PromotionsComponent } from './Components/promotions/promotions.component';
import { ContactComponent } from './Components/contact/contact.component';
import { NewAccountComponent } from './Components/new-account/new-account.component';
import { AirLoginComponent } from './Airport-view/air-login/air-login.component';
import { AirNewAccountComponent } from './Airport-view/air-new-account/air-new-account.component';
import { AirBookingComponent } from './Airport-view/air-booking/air-booking.component';
import { AirHomeComponent } from './Airport-view/air-home/air-home.component';
import { CheckInComponent } from './Airport-view/check-in/check-in.component';
import { LuggageComponent } from './Airport-view/luggage/luggage.component';
import { ExecutionComponent } from './Components/execution/execution.component';
import { AirPromotionsComponent } from './Airport-view/air-promotions/air-promotions.component';
import { FlightManagementComponent } from './Airport-view/flight-management/flight-management.component';
import { OpeningFlightsComponent } from './Airport-view/opening-flights/opening-flights.component';
import { CloseFlightsComponent } from './Airport-view/close-flights/close-flights.component';
import { AdminGuard } from './Services/admin-guard';
export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Inicio',
  },
  {
    path: 'date/:selectedOrigin/:selectedDestination',
    component: DateComponent,
    title: 'Fechas',
  },
  {
    path: 'app-contact',
    component: ContactComponent,
    title: 'Contacto',
  },
  {
    path: 'booking',
    component: BookingComponent,
    title: 'Reservar',
  },
  {
    path: 'app-promotions',
    component: PromotionsComponent,
    title: 'Promos',
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
    path: 'air-home',
    component: AirHomeComponent,
    title: 'AirHome',
    canActivate: [AdminGuard],
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
    path: 'app-air-booking',
    component: AirBookingComponent,
    title: 'AirReservas',
  },
  {
    path: 'check-in',
    component: CheckInComponent,
    title: 'ChequeoPasajeros',
    canActivate: [AdminGuard],
  },
  {
    path: 'luggage/:idReservation/:seats/:numeroAsiento',
    component: LuggageComponent,
    title: 'ChequeoMaletas',
  },
  {
    path: 'flight-management',
    component: FlightManagementComponent,
    title: 'GestionVuelos',
    canActivate: [AdminGuard],
  },
  {
    path: 'executions',
    component: ExecutionComponent,
    title: 'Ejecuciones',
  },
  {
    path: 'app-opening-flights',
    component: OpeningFlightsComponent,
    title: 'Apertura',
    canActivate: [AdminGuard],
  },
  {
    path: 'app-air-promotions',
    component: AirPromotionsComponent,
    title: 'AirPromociones',
    canActivate: [AdminGuard],
  },
  {
    path: 'app-close-flights',
    component: CloseFlightsComponent,
    title: 'Cierre',
    canActivate: [AdminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

export const routingComponents = [
  HomeComponent,
  DateComponent,
  ExecutionComponent,
  BookingComponent,
  LoginComponent,
  PromotionsComponent,
  AirPromotionsComponent,
];
