import { Component, OnInit } from '@angular/core';
import { GenreService } from 'src/app/service/genre.service';
import { FormGroup, FormControl } from '@angular/forms';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-list-genre',
  templateUrl: './list-genre.component.html',
  styleUrls: ['./list-genre.component.css']
})
export class ListGenreComponent implements OnInit {
  public listGenre : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;

  genreForm = new FormGroup({
    name: new FormControl(''),
  });

  constructor(
    private genreSer : GenreService
  ) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
  }

  public refreshDataOnChange(){
    this.genreSer.genreChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);

        this.genreSer.message.subscribe((message:string) =>{
          if(message != "" || message != null){
            this.simpleAlert(message);
          }
        })
      }
    })
  }

  public loadListData(form:any){
    this.genreSer.getGenreByPage(form).subscribe((data) =>{
      this.listGenre = data.content.content;
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

  alertConfirmationDelete(genreId : number){
    Swal.fire({
      title: 'Bạn có chắc muốn xóa',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.genreSer.deleteGenre(genreId).subscribe((data) =>{
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
          'Hủy',
          'Đã hủy hành động',
          'error'
        )
      }
    })
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

  onSubmit() {
    const searchForm = new FormData();
    searchForm.append('name', this.genreForm.get("name")?.value);
    this.loadListData(searchForm);
  }

  onDelete(genreId: number){
    console.log(genreId);
    this.alertConfirmationDelete(genreId);
  }

}
