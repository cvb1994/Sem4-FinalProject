import { Component, OnInit } from '@angular/core';
import { AlbumService } from 'src/app/service/album.service';
import { FormGroup, FormControl } from '@angular/forms';
import { ArtistService } from 'src/app/service/artist.service';
import { UtilitiesService } from 'src/app/service/utilities.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-list-album',
  templateUrl: './list-album.component.html',
  styleUrls: ['./list-album.component.css']
})
export class ListAlbumComponent implements OnInit {
  public listAlbum : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;
  public listArtist:any;

  albumFormSearch = new FormGroup({
    name: new FormControl(''),
    artistId: new FormControl('')
  });
  
  constructor(
    private utilSer : UtilitiesService,
    private albumSer:AlbumService,
    private artistSer : ArtistService
    ) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
    this.artistSer.getArtistsOrderByName().subscribe((data) =>{
      this.listArtist = data.content;
      console.log(this.listArtist);
    })

    this.albumSer.message.subscribe((data:string) =>{
      if(data != "" || data != null){
        this.simpleAlert(data);
      }
    });
  }

  public refreshDataOnChange(){
    this.albumSer.albumChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);
      }
    })
  }

  public loadListData(form:any){
    this.albumSer.getAlbums(form).subscribe({
      next: (data) =>{
        this.listAlbum = data.content.content;
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
      },
      error: (err) => {
        this.utilSer.redirectToLogin();
      }
    });
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }

  onSubmit() {
    const searchForm = new FormData();
    searchForm.append('name', this.albumFormSearch.get("name")?.value);
    if(this.albumFormSearch.get("artistId")?.value != ''){
      searchForm.append('artistId', this.albumFormSearch.get("artistId")?.value);
    }
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

  onDelete(artistId: number){
    this.alertConfirmationDelete(artistId);
  }

  alertConfirmationDelete(artistId : number){
    Swal.fire({
      title: 'B???n c?? ch???c mu???n x??a?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.albumSer.deleteAlbum(artistId).subscribe((data) =>{
          if(data.status == true){
            Swal.fire(
              'Th??nh c??ng!',
              data.message,
              'success'
            )
            window.location.reload();
          }
        })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'H???y b???',
          'H??nh ?????ng ???? h???y',
          'error'
        )
      }
    })
  }

}
