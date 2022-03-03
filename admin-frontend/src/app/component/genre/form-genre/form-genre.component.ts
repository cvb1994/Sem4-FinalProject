import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
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
    name: new FormControl('', Validators.required),
    avatar: new FormControl(''),
  });

  get name(){return this.genreForm.get('name')};

  constructor(
    private genreSer : GenreService,
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.genreId = params.get('genreId');
      if(this.genreId != null){
        this.genreSer.getGenreById(this.genreId).subscribe((data) =>{
          this.editGenre = data.content;
          this.genreForm.get("id")?.setValue(data.content.id);
          this.genreForm.get("name")?.setValue(data.content.name);
          this.myImg.nativeElement.src = data.content.avatar;
          this.divStyle = 200;
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
      this.divStyle = 200;
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.genreForm.get("id")?.value);
    formData.append('name', this.genreForm.get("name")?.value);
    var file:any = this.genreForm.get("avatar");
    if(file.value != ""){
      formData.append('file', this.genreForm.get("avatar")?.value);
    }
    var id:any = this.genreForm.get("id")?.value;
    if(id == 0){
      this.spinner.show();
      this.genreSer.postGenre(formData).subscribe((data) =>{
        if(data.status == true){
          this.spinner.hide();
          this._router.navigateByUrl('list-genre')
          this.genreSer.genreChanged.next(true);
          this.genreSer.message.next(data.message);
        }
      });
      
    } else {
      formData.append('createdDate', this.editGenre.createdDate);
      this.genreSer.updateGenre(formData);
    }
  }
}
