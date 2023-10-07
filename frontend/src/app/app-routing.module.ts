import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { DateComponent } from './Components/date/date.component';
import { FlightComponent } from './Components/flight/flight.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home',
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
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
