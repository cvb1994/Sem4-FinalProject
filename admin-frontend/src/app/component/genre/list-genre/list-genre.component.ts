import { Component, OnInit } from '@angular/core';
import { GenreService } from 'src/app/service/genre.service';

@Component({
  selector: 'app-list-genre',
  templateUrl: './list-genre.component.html',
  styleUrls: ['./list-genre.component.css']
})
export class ListGenreComponent implements OnInit {
  public listGenre : any;

  constructor(
    private genreSer : GenreService
  ) { }

  ngOnInit(): void {
    this.genreSer.getAllGenre().subscribe((data) =>{
      this.listGenre = data.content;
      console.log(this.listGenre);
    });
  }

}
