import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { SystemParamService } from 'src/app/service/system-param.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form-system-param',
  templateUrl: './form-system-param.component.html',
  styleUrls: ['./form-system-param.component.css']
})
export class FormSystemParamComponent implements OnInit {
  public paramId:any;
  public editParam:any;

  paramForm = new FormGroup({
    id: new FormControl('0'),
    paramName: new FormControl('', Validators.required),
    paramValue: new FormControl('', Validators.required),
  });

  get paramName(){return this.paramForm.get('paramName')};
  get paramValue(){return this.paramForm.get('paramValue')};

  constructor(
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService,
    private paramSer : SystemParamService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.paramId = params.get('paramId');
      if(this.paramId != null){
        this.paramSer.getParamById(this.paramId).subscribe((data) =>{
          this.editParam = data.content;
          this.paramForm.get("id")?.setValue(data.content.id);
          this.paramForm.get("paramName")?.setValue(data.content.paramName);
          this.paramForm.get("paramValue")?.setValue(data.content.paramValue);
        });
      }
    });
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.paramForm.get("id")?.value);
    formData.append('paramName', this.paramForm.get("paramName")?.value);
    formData.append('paramValue', this.paramForm.get("paramValue")?.value);
   
    var id:any = this.paramForm.get("id")?.value;
    if(id == 0){
      this.spinner.show();
      this.paramSer.postParam(formData).subscribe((data) =>{
        if(data.status == true){
          this.spinner.hide();
          this.paramSer.paramChanged.next(true);
          this.paramSer.message.next(data.message);
          this._router.navigateByUrl('list-system-param');
        } else {
          this.spinner.hide();
          this.simpleAlert(data.message);
        }
      });
    } else {
      formData.append('createdDate', this.editParam.createdDate);
      this.spinner.show();
      this.paramSer.updataParam(formData).subscribe((data) => {
        if(data.status == true){
          console.log(data);
          this.spinner.hide();
          this._router.navigateByUrl('list-system-param');
          this.paramSer.paramChanged.next(true);
          this.paramSer.message.next(data.message);
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

}
