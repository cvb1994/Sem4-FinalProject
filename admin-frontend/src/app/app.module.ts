import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {MatChipsModule} from '@angular/material/chips';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSelectModule} from '@angular/material/select';

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
    FormSongComponent
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
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
