import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import Swal from 'sweetalert2'
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  public listUser : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;

  search = new FormControl('');

  constructor(private userSer: UserService) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
    this.userSer.message.subscribe((data:string) =>{
      if(data != "" || data != null){
        this.simpleAlert(data);
      }
    });
  }

  public refreshDataOnChange(){
    this.userSer.userChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);
      }
    })
  }

  public loadListData(form:any){
    this.userSer.getUsers(form).subscribe((data) =>{
      this.listUser = data.content.content;
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
    searchForm.append('username', this.search.value);
    searchForm.append('email', this.search.value);
    searchForm.append('phone', this.search.value);
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

  onDelete(userId: number){
    this.alertConfirmationDelete(userId);
  }

  alertConfirmationDelete(userId : number){
    Swal.fire({
      title: 'Are you sure?',
      text: 'Your Action cannot be rollback.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.value) {
        this.userSer.deleteUser(userId).subscribe((data) =>{
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
