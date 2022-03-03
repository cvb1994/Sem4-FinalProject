import { Component, OnInit } from '@angular/core';
import { ArtistService } from 'src/app/service/artist.service';
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

  artistFormSearch = new FormGroup({
    name: new FormControl(''),
    gender: new FormControl(''),
    nationality: new FormControl('')
  });


  constructor(private artistSer: ArtistService) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
  }

  public refreshDataOnChange(){
    this.artistSer.artistChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);

        this.artistSer.message.subscribe((message:string) =>{
          if(message != "" || message != null){
            this.simpleAlert(message);
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

}
