import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { ListGenreComponent } from './component/genre/list-genre/list-genre.component';
import { FormGenreComponent } from './component/genre/form-genre/form-genre.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ListGenreComponent,
    FormGenreComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
