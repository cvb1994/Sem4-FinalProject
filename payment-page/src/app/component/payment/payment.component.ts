import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/service/payment.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  private userId:any;
  public user : any;
  public listPayment:any;
  private paymentParamId:any;
  public total:any;

  constructor(
    private _Activatedroute:ActivatedRoute,
    private paymentSer : PaymentService,
    private userSer : UserService
  ) { }

  ngOnInit(): void {
    this._Activatedroute.paramMap.subscribe(params => {
      this.userId = params.get('userId');
    });

    this.userSer.getUserById(this.userId).subscribe((data)=>{
      this.user = data.content;
    })

    this.paymentSer.getPaymentOption().subscribe((data) =>{
      this.listPayment = data.content;
    })
  }

  clickSelect(elementId:any){
    this.listPayment.forEach((e: { id: any; select: boolean; discount: number; price: any; actualPrice: any; }) => {
      if(e.id == elementId){
        e.select = true;
        this.paymentParamId = e.id;
        if(e.discount == 0){
          this.total = e.price;
        } else {
          this.total = e.actualPrice;
        }
      } else {
        e.select = false;
      }
    });
  }

  submitPayment(){
    const form = new FormData();
    form.append("paymentParamId", this.paymentParamId);
    form.append("userId", this.user.id);
    form.append("orderType","250000");
    form.append("orderInfo", "Thanh toan");
    this.paymentSer.getPaymentPage(form).subscribe((data) =>{
      if(data.status == true){
        window.location.href = data.content;
      }
    })
  }

}
