import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
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
  private localStorage: Storage = localStorage;


  artistSelectCtrl = new FormControl();
  filterListArtist: Observable<any>;
  @ViewChild('artistSelectInput')
  artistSelectInput!: ElementRef<HTMLInputElement>;

  genreSelectCtrl = new FormControl();
  filterListgenre: Observable<any>;
  @ViewChild('genreSelectInput')
  genreSelectInput!: ElementRef<HTMLInputElement>;

  audio = new Audio();

  @ViewChild('previewImg')
  public myImg!: ElementRef;

  songForm = new FormGroup({
    id: new FormControl('0'),
    title: new FormControl('', Validators.required),
    composer: new FormControl(''),
    timePlay: new FormControl(''),
    vipOnly: new FormControl('false'),
    artistIds: new FormControl(''),
    genreIds: new FormControl(''),
    albumId: new FormControl('0'),
    image: new FormControl(''),
    mediaUrl: new FormControl(''),
  });

  get title(){return this.songForm.get('title')}

  constructor(
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
  }

  selectedArtist(event: MatAutocompleteSelectedEvent): void {
    this.listArtistSelect.push(event.option.value);
    let index = this.listArtist.indexOf(event.option.value);
    if(index >= 0){
      this.listArtist.splice(index,1);
    }
    this.artistSelectInput.nativeElement.value = '';
    this.artistSelectCtrl.setValue(null);
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
  }

  selectedGenre(event: MatAutocompleteSelectedEvent): void {
    this.listGenreSelect.push(event.option.value);
    let index = this.listGenre.indexOf(event.option.value);
    if(index >= 0){
      this.listGenre.splice(index,1);
    }
    this.genreSelectInput.nativeElement.value = '';
    this.genreSelectCtrl.setValue(null);
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
          this.audio.src = data.content.mediaUrl;
          this.audio.load();
          this.audioLoaded = true;
          this.myImg.nativeElement.src = data.content.image;
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
            console.log(this.listArtist);
          });

        });
      }
    });
  }

  getList(){
    this.artistSer.getArtistsOrderByName().subscribe((data) =>{
      this.listArtist = data.content;
    });
    this.genreSer.getGenresOrderByName().subscribe((data) =>{
      this.listGenre = data.content;
    });
    this.albumSer.getAllOrderByName().subscribe((data)=>{
      this.listAlbum = data.content;
    });
  };



  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.songForm.get('image')?.setValue(file);
      this.myImg.nativeElement.src = URL.createObjectURL(file);
      this.divStyle = 200;
    }
  }

  onAudioSelect(event:any){
    if (event.target.files.length > 0) {
      const mp3 = event.target.files[0];
      this.songForm.get('mediaUrl')?.setValue(mp3);
      this.audio.src = URL.createObjectURL(mp3);
      this.audio.load();
      this.audioLoaded = true;
    }
  }

  onPlayPause(){
    this.audioPlaying = !this.audioPlaying;
    if(this.audioPlaying == true){
      this.audio.play();
    } else {
      this.audio.pause();
    }
  }

  onSubmit() {
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
    if(mp3.value != ""){
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
          this.songSer.songChanged.next(true);
          this.songSer.message.next(data.message);
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

}

