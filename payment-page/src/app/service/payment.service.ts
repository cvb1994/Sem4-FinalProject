import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }
  private baseurl = environment.apiUrl;

  public getPaymentPage(form:any): Observable<any>{
    const url = `${this.baseurl}/api/payment`;
    return this.http.post(url, form);
  }

  public getPaymentOption():Observable<any>{
    const url = `${this.baseurl}/api/paymentParam`;
    return this.http.get(url);
  }
}
