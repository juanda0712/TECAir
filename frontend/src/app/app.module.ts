import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { TestComponent } from './Components/test/test.component';
import { NavBarComponent } from './Components/Reusables/nav-bar/nav-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './Components/login/login.component';
import { ContactComponent } from './Components/contact/contact.component';
import { PromotionsComponent } from './Components/promotions/promotions.component';
import { NewAccountComponent } from './Components/new-account/new-account.component';
import { AirNewAccountComponent } from './Airport-view/air-new-account/air-new-account.component';
import { AirLoginComponent } from './Airport-view/air-login/air-login.component';
import { AirBookingComponent } from './Airport-view/air-booking/air-booking.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { HttpClientModule } from '@angular/common/http';
import { ExecutionComponent } from './Components/execution/execution.component';
import { ExecutionCardComponent } from './Components/Reusables/execution-card/execution-card.component';
import { FlightManagementComponent } from './Airport-view/flight-management/flight-management.component';
import { LuggageComponent } from './Airport-view/luggage/luggage.component';
import { OpeningFlightsComponent } from './Airport-view/opening-flights/opening-flights.component';
import { CloseFlightsComponent } from './Airport-view/close-flights/close-flights.component';
import { AirPromotionsComponent } from './Airport-view/air-promotions/air-promotions.component';
import { PromotionCardComponent } from './Components/Reusables/promotion-card/promotion-card.component';

@NgModule({
  declarations: [
    routingComponents, //esta es la variable que se exporta de app-routing, por eso no hay que importar los componentes aca
    AppComponent,
    TestComponent,
    NavBarComponent,
    LoginComponent,
    ContactComponent,
    NewAccountComponent,
    AirNewAccountComponent,
    AirLoginComponent,
    AirBookingComponent,
    ExecutionComponent,
    ExecutionCardComponent,
    FlightManagementComponent,
    LuggageComponent,
    OpeningFlightsComponent,
    CloseFlightsComponent,
    OpeningFlightsComponent,
    AirPromotionsComponent,
    PromotionCardComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
