import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import Swal from 'sweetalert2';
import { SongService } from 'src/app/service/song.service';
import { ArtistService } from 'src/app/service/artist.service';
import { GenreService } from 'src/app/service/genre.service';
import { AlbumService } from 'src/app/service/album.service';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import { UtilitiesService } from 'src/app/service/utilities.service';
import { AbstractControl, ValidationErrors } from '@angular/forms';

@Component({
  selector: 'app-form-song',
  templateUrl: './form-song.component.html',
  styleUrls: ['./form-song.component.css']
})

export class FormSongComponent implements OnInit {
  public songId:any;
  public editSong:any;
  public divStyle:any;
  public listArtist:any;
  public listArtistSelect:any[] = [];
  public listGenre:any;
  public listGenreSelect:any[] = [];
  public audioLoaded:boolean = false;
  public audioPlaying:boolean = false;
  public listAlbum:any;
  public duration:any;

  artistSelectCtrl = new FormControl();
  listImageExten: any;
  get artistSelect(){return this.artistSelectCtrl};
  filterListArtist: Observable<any>;
  @ViewChild('artistSelectInput')
  artistSelectInput!: ElementRef<HTMLInputElement>;

  genreSelectCtrl = new FormControl();
  get genreSelect(){return this.genreSelectCtrl};
  filterListgenre: Observable<any>;
  @ViewChild('genreSelectInput')
  genreSelectInput!: ElementRef<HTMLInputElement>;

  @ViewChild('audioPreview')
  public myAudio!: ElementRef;

  @ViewChild('previewImg')
  public myImg!: ElementRef;

  @ViewChild('preview')
  public bigImg!: ElementRef;

  songForm = new FormGroup({
    id: new FormControl('0'),
    title: new FormControl('', Validators.required),
    composer: new FormControl(''),
    timePlay: new FormControl('',Validators.pattern('^([0-9])+:+([0-9])+$')),
    vipOnly: new FormControl('false'),
    artistIds: new FormControl('', Validators.required),
    genreIds: new FormControl('', Validators.required),
    albumId: new FormControl('0'),
    image: new FormControl(''),
    mediaUrl: new FormControl('', Validators.required),
  });

  get title(){return this.songForm.get('title')};
  get timePlay(){return this.songForm.get('timePlay')};
  get mediaUrl(){return this.songForm.get('mediaUrl')};
  get image(){return this.songForm.get('image')};

  constructor(
    private utilSer : UtilitiesService,
    private albumSer : AlbumService,
    private songSer : SongService,
    private artistSer: ArtistService,
    private genreSer: GenreService,
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService
  ) {
    this.filterListArtist = this.artistSelectCtrl.valueChanges.pipe(
      startWith(null),
      map((text: string | null) => (text ? this._filterArtist(text) : this.listArtist)),
    );
    this.filterListgenre = this.genreSelectCtrl.valueChanges.pipe(
      startWith(null),
      map((text: string | null) => (text ? this._filterGenre(text) : this.listGenre)),
    );
   }

  removeArtist(artist:any): void {
    const index = this.listArtistSelect.indexOf(artist);
    if (index >= 0) {
      this.listArtistSelect.splice(index, 1);
      this.listArtist.push(artist);
      this.listArtist.sort((a:any,b:any) =>(a.name > b.name) ? 1 :((b.name > a.name) ? -1 : 0));
    }
    if(this.listArtistSelect.length == 0){
      this.songForm.get("artistIds")?.setValue("");
    }
  }

  selectedArtist(event: MatAutocompleteSelectedEvent): void {
    this.listArtistSelect.push(event.option.value);
    let index = this.listArtist.indexOf(event.option.value);
    if(index >= 0){
      this.listArtist.splice(index,1);
    }
    this.artistSelectInput.nativeElement.value = '';
    this.artistSelectCtrl.setValue(null);
    this.songForm.get("artistIds")?.setValue("valid");
  }

  private _filterArtist(value: any): string[] {
    let filterValue : any;
    if(typeof value == "string"){
      filterValue = value.toLowerCase();
    } else {
      filterValue = value.name.toLowerCase();
    }
    let filterArray = this.listArtist.filter(function(el: { name: string; }){
      return el.name.toLowerCase().includes(filterValue);
    });
    return filterArray;
  }

  removeGenre(genre:any): void {
    const index = this.listGenreSelect.indexOf(genre);
    if (index >= 0) {
      this.listGenreSelect.splice(index, 1);
      this.listGenre.push(genre);
      this.listGenre.sort((a:any,b:any) =>(a.name > b.name) ? 1 :((b.name > a.name) ? -1 : 0));
    }
    if(this.listGenreSelect.length === 0){
      this.songForm.get("genreIds")?.setValue("");
    }
  }

  selectedGenre(event: MatAutocompleteSelectedEvent): void {
    this.listGenreSelect.push(event.option.value);
    let index = this.listGenre.indexOf(event.option.value);
    if(index >= 0){
      this.listGenre.splice(index,1);
    }
    this.genreSelectInput.nativeElement.value = '';
    this.genreSelectCtrl.setValue(null);
    this.songForm.get("genreIds")?.setValue("valid");
  }

  private _filterGenre(value: any): string[] {
    let filterValue : any;
    if(typeof value == "string"){
      filterValue = value.toLowerCase();
    } else {
      filterValue = value.name.toLowerCase();
    }
    let filterArray = this.listGenre.filter(function(el: { name: string; }){
      return el.name.toLowerCase().includes(filterValue);
    });
    return filterArray;
  }

  ngOnInit(): void {
    this.getInitData();
  }


  getInitData(){
    this.listImageExten = this.utilSer.getListImageExtension();
    this._Activatedroute.paramMap.subscribe(params => {
      this.songId = params.get('songId');
      if(this.songId != null){
        this.songSer.getSongById(this.songId).subscribe((data) =>{
          this.editSong = data.content;
          console.log(data.content);
          this.songForm.get("id")?.setValue(data.content.id);
          this.songForm.get("title")?.setValue(data.content.title);
          this.songForm.get("composer")?.setValue(data.content.composer);
          this.songForm.get("timePlay")?.setValue(data.content.timePlay);
          this.songForm.get("albumId")?.setValue(data.content.album?.id);
          this.songForm.get("vipOnly")?.setValue(data.content.vipOnly);
          this.songForm.get("artistIds")?.setValue("valid");
          this.songForm.get("genreIds")?.setValue("valid");
          this.songForm.get("mediaUrl")?.setValue("valid");
          this.myAudio.nativeElement.src = data.content.mediaUrl;
          this.audioLoaded = true;
          this.myImg.nativeElement.src = data.content.image;
          this.bigImg.nativeElement.src = data.content.image;
          this.divStyle = 200;
          data.content.artists.forEach((a: any) => {
            this.listArtistSelect.push(a);
          });
          data.content.genres.forEach((g: any) => {
            this.listGenreSelect.push(g);
          });
          this.artistSer.getArtistsOrderByName().subscribe((data) =>{
            this.listArtist = data.content;
            this.listArtistSelect.forEach((a:any)=>{
              this.listArtist = this.listArtist.filter(function(el: { id: number; }){
                return el.id != a.id;
              });
            });
          });

          this.genreSer.getGenresOrderByName().subscribe((data) =>{
            this.listGenre = data.content;
            this.listGenreSelect.forEach((g:any) =>{
              this.listGenre = this.listGenre.filter(function(el : {id:number;}){
                return el.id != g.id;
              })
            })
          });

          this.albumSer.getAllOrderByName().subscribe((data)=>{
            this.listAlbum = data.content;
          });

        });
      } else {
        this.artistSer.getArtistsOrderByName().subscribe((data) =>{
          this.listArtist = data.content;
        });

        this.genreSer.getGenresOrderByName().subscribe((data) =>{
          this.listGenre = data.content;
        });

        this.albumSer.getAllOrderByName().subscribe((data)=>{
          this.listAlbum = data.content;
        });
      }
    });
  }

  onFileSelect(event:any) {
    if(this.listImageExten.includes(event.target.files[0].type)){
      if (event.target.files.length > 0) {
        const file = event.target.files[0];
        this.songForm.get('image')?.setValue(file);
        this.myImg.nativeElement.src = URL.createObjectURL(file);
        this.bigImg.nativeElement.src = URL.createObjectURL(file);
        this.divStyle = 200;
      }
    } else {
      this.songForm.get("image")?.setErrors({'invalid':true});
      this.simpleAlert("Định dạng ảnh không hỗ trợ");
    }
  }

  onAudioSelect(event:any){
    if(event.target.files[0].type == 'audio/mpeg'){
      if (event.target.files.length > 0) {
        const mp3 = event.target.files[0];
        this.songForm.get('mediaUrl')?.setValue(mp3);
        this.myAudio.nativeElement.src = URL.createObjectURL(mp3);
      }
    } else {
      this.songForm.get("mediaUrl")?.setErrors({'invalid':true});
      this.simpleAlert("Định dạng nhạc không hỗ trợ");
    }
  }

  setDuration(load_event:any): void {
    this.duration = Math.round(load_event.currentTarget.duration);
    this.songForm.get("timePlay")?.setValue(durationConvert(load_event.currentTarget.duration));
  }

  onSubmit() {
    if(this.artistValidatation() && this.genreValidatation()){
      const formData = new FormData();
      formData.append('id', this.songForm.get("id")?.value);
      formData.append('title', this.songForm.get("title")?.value);
      formData.append('composer', this.songForm.get("composer")?.value);
      formData.append('timePlay', this.songForm.get("timePlay")?.value);
      formData.append('vipOnly', this.songForm.get("vipOnly")?.value);
      if(this.listArtistSelect.length > 0){
        formData.append('artistIds', this.listArtistId());
      }
      if(this.listGenreSelect.length > 0){
        formData.append('genreIds', this.listGenreId());
      }
      formData.append('albumId', this.songForm.get("albumId")?.value);
      var file:any = this.songForm.get("image");
      if(file.value != ""){
        formData.append('file', this.songForm.get("image")?.value);
      }
      var mp3:any = this.songForm.get("mediaUrl");
      if(mp3.value != "" && mp3.value != "valid"){
        formData.append('mp3', this.songForm.get("mediaUrl")?.value);
      }
      var id:any = this.songForm.get("id")?.value;
      if(id == 0){
        this.spinner.show();
        this.songSer.postSong(formData).subscribe((data) =>{
          if(data.status == true){
            this.spinner.hide();
            this.songSer.songChanged.next(true);
            this.songSer.message.next(data.message);
            this._router.navigateByUrl('list-song');
          } else {
            this.spinner.hide();
            this.simpleAlert(data.message);
          }
        });
      } else {
        formData.append('createdDate', this.editSong.createdDate);
        this.spinner.show();
        this.songSer.updateSong(formData).subscribe((data) => {
          if(data.status == true){
            this.spinner.hide();
            this._router.navigateByUrl('list-song');
            console.log(data.message);
            console.log("da vao day roifn");
            this.songSer.songChanged.next(true);
            this.songSer.message.next(data.message);
          } else {
            this.spinner.hide();
            this.simpleAlert(data.message);
          }
        })
      }
    }
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }

  listArtistId() : any{
    let listId : any[] = [];
    this.listArtistSelect.forEach(e => {
      listId.push(e.id);
    })
    return listId;
  }

  listGenreId() : any{
    let listId : any[] = [];
    this.listGenreSelect.forEach(e => {
      listId.push(e.id);
    })
    return listId;
  }

  artistValidatation():boolean{
    if(this.listArtistSelect.length <= 0){
      console.log("da vao");
      this.artistSelectCtrl.setErrors({'invalid':true})
      return false;
    }
    return true;
  }

  genreValidatation():boolean{
    if(this.listGenreSelect.length <= 0){
      this.genreSelectCtrl.setErrors({'invalid':true})
      return false;
    }
    return true;
  }
}

function durationConvert(duration: number):string {
  let a = Math.round(duration);
  let minute = Math.floor(a/60);
  let second = (a - (minute*60));
  return minute+":"+ second;
}

