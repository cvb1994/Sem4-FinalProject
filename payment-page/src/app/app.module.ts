import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import localeVN from '@angular/common/locales/vi'
registerLocaleData(localeVN);

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { PaymentComponent } from './component/payment/payment.component';
import { ReturnPageComponent } from './component/return-page/return-page.component';
import { ReturnPageFailedComponent } from './component/return-page-failed/return-page-failed.component';

@NgModule({
  declarations: [
    AppComponent,
    PaymentComponent,
    ReturnPageComponent,
    ReturnPageFailedComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'vi' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
