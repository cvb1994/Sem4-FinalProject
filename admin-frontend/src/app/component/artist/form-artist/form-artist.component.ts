import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { ArtistService } from 'src/app/service/artist.service';
import { UtilitiesService } from 'src/app/service/utilities.service';

@Component({
  selector: 'app-form-artist',
  templateUrl: './form-artist.component.html',
  styleUrls: ['./form-artist.component.css']
})
export class FormArtistComponent implements OnInit {
  public artistId:any;
  public editArtist:any;
  public divStyle:any;
  public listCountries:any;

  @ViewChild('previewImg')
  public myImg!: ElementRef;

  artistForm = new FormGroup({
    id: new FormControl('0'),
    name: new FormControl('', Validators.required),
    gender: new FormControl(''),
    birthday: new FormControl(''),
    description: new FormControl(''),
    nationality: new FormControl(''),
    avatar: new FormControl(''),
  });

  get name(){return this.artistForm.get('name')};

  constructor(
    private utilSer : UtilitiesService,
    private artistSer : ArtistService,
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.artistId = params.get('artistId');
      if(this.artistId != null){
        this.artistSer.getArtistById(this.artistId).subscribe((data) =>{
          this.editArtist = data.content;
          this.artistForm.get("id")?.setValue(data.content.id);
          this.artistForm.get("name")?.setValue(data.content.name);
          this.artistForm.get("gender")?.setValue(data.content.gender);
          this.artistForm.get("birthday")?.setValue(data.content.birthday);
          this.artistForm.get("description")?.setValue(data.content.description);
          this.artistForm.get("nationality")?.setValue(data.content.nationality);
          this.myImg.nativeElement.src = data.content.avatar;
          this.divStyle = 200;
        });
      }
    });

    this.listCountries = this.utilSer.getListCountries();
  }

  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.artistForm.get('avatar')?.setValue(file);
      this.myImg.nativeElement.src = URL.createObjectURL(file);
      this.divStyle = 200;
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.artistForm.get("id")?.value);
    formData.append('name', this.artistForm.get("name")?.value);
    formData.append('gender', this.artistForm.get("gender")?.value);
    formData.append('birthday', this.artistForm.get("birthday")?.value);
    formData.append('description', this.artistForm.get("description")?.value);
    formData.append('nationality', this.artistForm.get("nationality")?.value);
    var file:any = this.artistForm.get("avatar");
    if(file.value != ""){
      formData.append('file', this.artistForm.get("avatar")?.value);
    }
    var id:any = this.artistForm.get("id")?.value;
    if(id == 0){
      this.spinner.show();
      this.artistSer.postArtist(formData).subscribe((data) =>{
        if(data.status == true){
          this.spinner.hide();
          this._router.navigateByUrl('list-genre')
          this.artistSer.artistChanged.next(true);
          this.artistSer.message.next(data.message);
        }
      });
      
    } else {
      formData.append('createdDate', this.editArtist.createdDate);
      this.artistSer.updateArtist(formData);
    }
  }


}
