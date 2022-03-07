import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { PaymentParamService } from 'src/app/service/payment-param.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form-payment-param',
  templateUrl: './form-payment-param.component.html',
  styleUrls: ['./form-payment-param.component.css']
})
export class FormPaymentParamComponent implements OnInit {
  public paramId:any;
  public editParam:any;

  paramForm = new FormGroup({
    id: new FormControl('0'),
    price: new FormControl('', Validators.required),
    discount: new FormControl('0'),
    timeExpire: new FormControl('', Validators.required),
    unit: new FormControl('', Validators.required)
  });

  get price(){return this.paramForm.get('price')};
  get timeExpire(){return this.paramForm.get('timeExpire')};
  get unit(){return this.paramForm.get('unit')};

  constructor(
    private _Activatedroute:ActivatedRoute,
    private _router: Router,
    private spinner: NgxSpinnerService,
    private paramSer : PaymentParamService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.paramId = params.get('paramId');
      if(this.paramId != null){
        this.paramSer.getPaymentParamById(this.paramId).subscribe((data) =>{
          this.editParam = data.content;
          this.paramForm.get("id")?.setValue(data.content.id);
          this.paramForm.get("price")?.setValue(data.content.price);
          this.paramForm.get("discount")?.setValue(data.content.discount);
          this.paramForm.get("timeExpire")?.setValue(data.content.timeExpire);
          this.paramForm.get("unit")?.setValue(data.content.unit);
        });
      }
    });
  }

  
  onSubmit() {
    const formData = new FormData();
    formData.append('id', this.paramForm.get("id")?.value);
    formData.append('price', this.paramForm.get("price")?.value);
    formData.append('discount', this.paramForm.get("discount")?.value);
    formData.append('timeExpire', this.paramForm.get("timeExpire")?.value);
    formData.append('unit', this.paramForm.get("unit")?.value);
   
    var id:any = this.paramForm.get("id")?.value;
    if(id == 0){
      this.spinner.show();
      this.paramSer.postPayemtnParam(formData).subscribe((data) =>{
        if(data.status == true){
          this.spinner.hide();
          this.paramSer.paymentParamChanged.next(true);
          this.paramSer.message.next(data.message);
          this._router.navigateByUrl('list-payemnt-param');
        } else {
          this.spinner.hide();
          this.simpleAlert(data.message);
        }
      });
    } else {
      formData.append('createdDate', this.editParam.createdDate);
      this.spinner.show();
      this.paramSer.updatePaymentParam(formData).subscribe((data) => {
        if(data.status == true){
          console.log(data);
          this.spinner.hide();
          this._router.navigateByUrl('list-payemnt-param');
          this.paramSer.paymentParamChanged.next(true);
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
