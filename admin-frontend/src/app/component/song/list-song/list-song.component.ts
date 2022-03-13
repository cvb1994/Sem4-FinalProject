import { Component, OnInit } from '@angular/core';
import { SongService } from 'src/app/service/song.service';
import { ArtistService } from 'src/app/service/artist.service';
import { GenreService } from 'src/app/service/genre.service';
import { FormGroup, FormControl } from '@angular/forms';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-list-song',
  templateUrl: './list-song.component.html',
  styleUrls: ['./list-song.component.css']
})
export class ListSongComponent implements OnInit {
  public listSong : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;
  listArtist:any;
  listGenre:any;

  songFormSearch = new FormGroup({
    title: new FormControl(''),
    composer: new FormControl(''),
    artistIds: new FormControl(''),
    genreIds: new FormControl(''),
  });

  toppings = new FormControl();

  constructor(
    private songSer : SongService,
    private artistSer : ArtistService,
    private genreSer : GenreService
  ) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
    this.artistSer.getArtistsOrderByName().subscribe((data) =>{
      this.listArtist = data.content;
    })
    this.genreSer.getGenresOrderByName().subscribe((data) =>{
      this.listGenre = data.content;
    })
    this.songSer.message.subscribe((message:string) =>{
      if(message != "" || message != null){
        this.simpleAlert(message);
      }
    });
  }

  public refreshDataOnChange(){
    this.songSer.songChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);
      }
    })
  }

  public loadListData(form:any){
    this.songSer.getSongs(form).subscribe((data) =>{
      this.listSong = data.content.content;
      console.log(data);
      this.currentPage = data.content.page;
      if(data.content.first == true && data.content.last == true){
        this.previousPageDisable = true;
        this.nextPageDisable = true;
      } else if(data.content.first == false && data.content.last == false){
        this.previousPageDisable = false;
        this.nextPageDisable = false;
      } else if(data.content.first == true){
        this.previousPageDisable = true;
        this.nextPageDisable = false;
      } else if(data.content.last == true){
        this.previousPageDisable = false;
        this.nextPageDisable = true;
      }
    });
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }

  onSubmit() {
    const searchForm = new FormData();
    searchForm.append('title', this.songFormSearch.get("title")?.value);
    searchForm.append('composer', this.songFormSearch.get("composer")?.value);
    searchForm.append('artistIds', this.songFormSearch.get("artistIds")?.value);
    searchForm.append('genreIds', this.songFormSearch.get("genreIds")?.value);
    this.loadListData(searchForm);
  }

  public nextPage(){
    this.currentPage = this.currentPage + 1;
    this.changePage(this.currentPage);
  }

  public previusPage(){
    this.currentPage = this.currentPage - 1;
    this.changePage(this.currentPage);
  }

  public changePage(page:any){
    var form = new FormData();
    form.append('page', page);
    this.loadListData(form);
  }

  onDelete(songId: number){
    this.alertConfirmationDelete(songId);
  }

  alertConfirmationDelete(artistId : number){
    Swal.fire({
      title: 'Bạn có chắc muốn xóa?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.songSer.deleteSong(artistId).subscribe((data) =>{
          if(data.status == true){
            Swal.fire(
              'Thành công!',
              data.message,
              'success'
            )
            window.location.reload();
          }
        })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'Hủy bỏ',
          'Đã hủy hành động',
          'error'
        )
      }
    })
  }

}
