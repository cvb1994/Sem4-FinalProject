import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import Swal from 'sweetalert2'
import { SystemParamService } from 'src/app/service/system-param.service';

@Component({
  selector: 'app-list-system-param',
  templateUrl: './list-system-param.component.html',
  styleUrls: ['./list-system-param.component.css']
})
export class ListSystemParamComponent implements OnInit {
  public listParam : any;
  public currentPage : any = 0;
  previousPageDisable : any = false;
  nextPageDisable : any = false;

  constructor(private paramSer:SystemParamService) { }

  ngOnInit(): void {
    var formData = new FormData();
    this.loadListData(formData);
    this.refreshDataOnChange();
    
    this.paramSer.message.subscribe((data:string) =>{
      if(data != "" || data != null){
        this.simpleAlert(data);
      }
    });
  }

  public refreshDataOnChange(){
    this.paramSer.paramChanged.subscribe((data:boolean) =>{
      if(data){
        var formData = new FormData();
        this.loadListData(formData);
      }
    })
  }

  public loadListData(form:any){
    this.paramSer.getParams(form).subscribe((data) =>{
      this.listParam = data.content.content;
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

  onDelete(paramId: number){
    this.alertConfirmationDelete(paramId);
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
        this.paramSer.deleteParam(artistId).subscribe((data) =>{
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
