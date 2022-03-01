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
    this.genreSer.getGenreByPage(formData).subscribe((data) =>{
      this.listGenre = data.content.content;
      this.currentPage = data.content.page;
      this.previousPageDisable = true;
    });
    this.loadListData();
  }

  public loadListData(){
    this.genreSer.genreChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.genreSer.getGenreByPage(formData).subscribe((data) =>{
          this.listGenre = data.content.content;
          this.currentPage = data.content.page;
          this.previousPageDisable = true;
        });

        this.genreSer.message.subscribe((message:string) =>{
          if(message != "" || message != null){
            this.simpleAlert(message);
          }
        })
      }
    })
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }

  alertConfirmationDelete(genreId : number){
    Swal.fire({
      title: 'Are you sure?',
      text: 'Your Action cannot be rollback.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.genreSer.deleteGenre(genreId).subscribe((data) =>{
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

  public nextPage(){
    this.currentPage = this.currentPage + 1;
    var form = new FormData();
    form.append('page', this.currentPage);
    this.genreSer.getGenreByPage(form).subscribe((data) =>{
      this.listGenre = data.content.content;
      this.currentPage = data.content.page;
      this.previousPageDisable = false;
      if(data.content.last == true){
        this.nextPageDisable = true;
      }
    });
  }

  public previusPage(){
    this.currentPage = this.currentPage - 1;
    var form = new FormData();
    form.append('page', this.currentPage);
    this.genreSer.getGenreByPage(form).subscribe((data) =>{
      this.listGenre = data.content.content;
      this.currentPage = data.content.page;
      this.nextPageDisable = false;
      if(data.content.first == true){
        this.previousPageDisable = true;
      }
    });
  }

  onSubmit() {
    const searchDate = new FormData();
    searchDate.append('name', this.genreForm.get("name")?.value);
    console.log(this.genreForm.get("name")?.value);
    this.genreSer.getGenreByPage(searchDate).subscribe((data) =>{
      this.listGenre = data.content.content;
      this.currentPage = data.content.page;
      this.previousPageDisable = true;
    });
  }

  onDelete(genreId: number){
    console.log(genreId);
    this.alertConfirmationDelete(genreId);
  }

}
