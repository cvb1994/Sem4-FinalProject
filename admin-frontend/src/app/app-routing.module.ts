import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { ListGenreComponent } from './component/genre/list-genre/list-genre.component';
import { FormGenreComponent } from './component/genre/form-genre/form-genre.component';
import { ListArtistComponent } from './component/artist/list-artist/list-artist.component';
import { FormArtistComponent } from './component/artist/form-artist/form-artist.component';

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'list-genre', component: ListGenreComponent},
  {path: 'form-genre', component: FormGenreComponent},
  {path: 'form-genre/:genreId', component: FormGenreComponent},
  {path: 'list-artist', component: ListArtistComponent},
  {path: 'form-artist', component: FormArtistComponent},
  {path: 'form-artist/:artistId', component: FormArtistComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
