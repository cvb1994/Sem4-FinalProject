import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {MatChipsModule} from '@angular/material/chips';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import { NgChartsModule } from 'ng2-charts';
import {MatDatepickerModule} from '@angular/material/datepicker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { ListGenreComponent } from './component/genre/list-genre/list-genre.component';
import { FormGenreComponent } from './component/genre/form-genre/form-genre.component';
import { ListArtistComponent } from './component/artist/list-artist/list-artist.component';
import { FormArtistComponent } from './component/artist/form-artist/form-artist.component';
import { ListAlbumComponent } from './component/album/list-album/list-album.component';
import { FormAlbumComponent } from './component/album/form-album/form-album.component';
import { ListSongComponent } from './component/song/list-song/list-song.component';
import { FormSongComponent } from './component/song/form-song/form-song.component';
import { ListUserComponent } from './component/user/list-user/list-user.component';
import { ListPaymentComponent } from './component/payment/list-payment/list-payment.component';
import { ListPaymentParamComponent } from './component/paymentParam/list-payment-param/list-payment-param.component';
import { FormPaymentParamComponent } from './component/paymentParam/form-payment-param/form-payment-param.component';
import { ListSystemParamComponent } from './component/systemParam/list-system-param/list-system-param.component';
import { FormSystemParamComponent } from './component/systemParam/form-system-param/form-system-param.component';
import { JwtInterceptor } from './helper/jwt.interceptor';
import { ListCountComponent } from './component/listenCount/list-count/list-count.component';
import { DetailCountComponent } from './component/listenCount/detail-count/detail-count.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ListGenreComponent,
    FormGenreComponent,
    ListArtistComponent,
    FormArtistComponent,
    ListAlbumComponent,
    FormAlbumComponent,
    ListSongComponent,
    FormSongComponent,
    ListUserComponent,
    ListPaymentComponent,
    ListPaymentParamComponent,
    FormPaymentParamComponent,
    ListSystemParamComponent,
    FormSystemParamComponent,
    ListCountComponent,
    DetailCountComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    BrowserAnimationsModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatInputModule,
    NgChartsModule,
    MatDatepickerModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
