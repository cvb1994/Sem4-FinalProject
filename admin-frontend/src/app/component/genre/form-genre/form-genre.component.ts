import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { GenreService } from 'src/app/service/genre.service';

@Component({
  selector: 'app-form-genre',
  templateUrl: './form-genre.component.html',
  styleUrls: ['./form-genre.component.css']
})
export class FormGenreComponent implements OnInit {
  public genreId:any;
  public editGenre:any;
  public divStyle:any;

  @ViewChild('previewImg')
  public myImg!: ElementRef;

  

  genreForm = new FormGroup({
    id: new FormControl('0'),
    name: new FormControl(''),
    avatar: new FormControl(''),
  });

  constructor(
    private genreSer : GenreService,
    private _Activatedroute:ActivatedRoute
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.genreId = params.get('genreId');
      if(this.genreId != null){
        this.genreSer.getGenreById(this.genreId).subscribe((data) =>{
          this.editGenre = data;
          console.log(this.editGenre);
        });
      }
    });

  }

  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.genreForm.get('avatar')?.setValue(file);
      this.myImg.nativeElement.src = URL.createObjectURL(file);
      this.divStyle = 300;
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.genreForm.get("id")?.value);
    formData.append('name', this.genreForm.get("name")?.value);
    formData.append('file', this.genreForm.get("avatar")?.value);
    this.genreSer.postGenre(formData);
  }

}
