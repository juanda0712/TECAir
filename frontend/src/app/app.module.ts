import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
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
import { AirFlightComponent } from './Airport-view/air-flight/air-flight.component';
import { AirBookingComponent } from './Airport-view/air-booking/air-booking.component';

@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
    NavBarComponent,
    LoginComponent,
    ContactComponent,
    NewAccountComponent,
    AirNewAccountComponent,
    AirLoginComponent,
    AirBookingComponent,

  ],
  imports: [
    //AirFlightComponent
    //PromotionsComponent
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
