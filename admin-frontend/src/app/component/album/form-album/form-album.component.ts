import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { AlbumService } from 'src/app/service/album.service';
import { ArtistService } from 'src/app/service/artist.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form-album',
  templateUrl: './form-album.component.html',
  styleUrls: ['./form-album.component.css']
})
export class FormAlbumComponent implements OnInit {
  public albumId:any;
  public editAlbum:any;
  public divStyle:any;
  public listArtist:any;

  @ViewChild('previewImg')
  public myImg!: ElementRef;

  albumForm = new FormGroup({
    id: new FormControl('0'),
    name: new FormControl('', Validators.required),
    artistId: new FormControl('', Validators.required),
    totalTime: new FormControl('', Validators.pattern('^([0-9])+h+\\s+([0-9])+m+\\s+([0-9])+s$')),
    releaseDate: new FormControl(''),
    avatar: new FormControl(''),
  });

  get name(){return this.albumForm.get('name')};
  get artistId(){return this.albumForm.get('artistId')};
  get totalTime(){return this.albumForm.get('totalTime')};

  constructor(
    private artistSer : ArtistService,
    private albumSer : AlbumService,
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.albumId = params.get('albumId');
      if(this.albumId != null){
        this.albumSer.getAlbumById(this.albumId).subscribe((data) =>{
          console.log(data.content);
          this.editAlbum = data.content;
          this.albumForm.get("id")?.setValue(data.content.id);
          this.albumForm.get("name")?.setValue(data.content.name);
          this.albumForm.get("artistId")?.setValue(data.content.artist.id);
          if(data.content.releaseDate != null){
            this.albumForm.get("releaseDate")?.setValue(data.content.releaseDate);
          }
          if(data.content.totalTime != null){
            this.albumForm.get("totalTime")?.setValue(data.content.totalTime);
          }
          this.myImg.nativeElement.src = data.content.avatar;
          this.divStyle = 200;
        });
      }
    });
    this.artistSer.getArtistsOrderByName().subscribe((data) =>{
      this.listArtist = data.content;
    })
  };
  
  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.albumForm.get('avatar')?.setValue(file);
      this.myImg.nativeElement.src = URL.createObjectURL(file);
      this.divStyle = 200;
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.albumForm.get("id")?.value);
    formData.append('name', this.albumForm.get("name")?.value);
    formData.append('artistId', this.albumForm.get("artistId")?.value);
    formData.append('releaseDate', this.albumForm.get("releaseDate")?.value);
    formData.append('totalTime', this.albumForm.get("totalTime")?.value);
    var file:any = this.albumForm.get("avatar");
    if(file.value != ""){
      formData.append('file', this.albumForm.get("avatar")?.value);
    }
    var id:any = this.albumForm.get("id")?.value;
    if(id == 0){
      this.spinner.show();
      this.albumSer.postAlbum(formData).subscribe((data) =>{
        console.log(data);
        if(data.status == true){
          console.log(data);
          this.spinner.hide();
          this.albumSer.albumChanged.next(true);
          this.albumSer.message.next(data.message);
          this._router.navigateByUrl('list-album');
        } else {
          this.spinner.hide();
          this.simpleAlert(data.message);
        }
      });
    } else {
      formData.append('createdDate', this.editAlbum.createdDate);
      this.spinner.show();
      this.albumSer.updateAlbum(formData).subscribe((data) => {
        if(data.status == true){
          console.log(data);
          this.spinner.hide();
          this._router.navigateByUrl('list-album');
          this.albumSer.albumChanged.next(true);
          this.albumSer.message.next(data.message);
        } else {
          this.spinner.hide();
          this.simpleAlert(data.message);
        }
      })
    }
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }

  
}
