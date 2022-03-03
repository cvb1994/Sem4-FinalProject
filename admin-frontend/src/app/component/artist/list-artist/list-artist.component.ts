import { Component, OnInit } from '@angular/core';
import { ArtistService } from 'src/app/service/artist.service';
import { UtilitiesService } from 'src/app/service/utilities.service';
import { FormGroup, FormControl } from '@angular/forms';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-list-artist',
  templateUrl: './list-artist.component.html',
  styleUrls: ['./list-artist.component.css']
})
export class ListArtistComponent implements OnInit {
  public listArtist : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;
  listCountries : any;

  artistFormSearch = new FormGroup({
    name: new FormControl(''),
    gender: new FormControl(''),
    nationality: new FormControl('')
  });


  constructor(
    private artistSer: ArtistService,
    private utilSer : UtilitiesService
  ) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
    this.listCountries = this.utilSer.getListCountries();
  }

  public refreshDataOnChange(){
    this.artistSer.artistChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);

        this.artistSer.message.subscribe((message:string) =>{
          if(message != "" || message != null){
            this.simpleAlert(message);
            console.log("da vao thong bao");
          }
        })
      }
    })
  }

  public loadListData(form:any){
    this.artistSer.getArtists(form).subscribe((data) =>{
      this.listArtist = data.content.content;
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
    searchForm.append('name', this.artistFormSearch.get("name")?.value);
    searchForm.append('gender', this.artistFormSearch.get("gender")?.value);
    searchForm.append('nationality', this.artistFormSearch.get("nationality")?.value);
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
      title: 'Are you sure?',
      text: 'Your Action cannot be rollback.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.artistSer.deleteArtist(artistId).subscribe((data) =>{
          if(data.status == true){
            Swal.fire(
              'Success!',
              data.message,
              'success'
            )
            window.location.reload();
          }
        })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'Cancelled',
          'Performed action record present in cloud and databstore.)',
          'error'
        )
      }
    })
  }

}
