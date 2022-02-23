import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { ListGenreComponent } from './component/genre/list-genre/list-genre.component';
import { FormGenreComponent } from './component/genre/form-genre/form-genre.component';

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'list-genre', component: ListGenreComponent},
  {path: 'form-genre', component: FormGenreComponent},
  {path: 'form-genre/:genreId', component: FormGenreComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
