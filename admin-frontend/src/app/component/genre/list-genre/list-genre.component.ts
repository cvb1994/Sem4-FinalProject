import { Component, OnInit } from '@angular/core';
import { ListGenreService } from 'src/app/service/genre/list-genre.service';

@Component({
  selector: 'app-list-genre',
  templateUrl: './list-genre.component.html',
  styleUrls: ['./list-genre.component.css']
})
export class ListGenreComponent implements OnInit {
  public listGenre : any;

  constructor(
    private listGenreSer : ListGenreService
  ) { }

  ngOnInit(): void {
    this.listGenreSer.getAllGenre().subscribe((data) =>{
      this.listGenre = data;
      console.log(this.listGenre);
    });
  }

}
