import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentParamService {
  public paymentParamChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) {
    this.paymentParamChanged = new Subject<boolean>();
    this.message = new Subject<string>();
   }

   private baseurl = environment.apiUrl;

   public getPaymentParams(form:any): Observable<any>{
    const url = `${this.baseurl}/api/paymentParam/list`;
    return this.http.post<any>(url, form);
  }

  public getPaymentParamById(paymentParamId:any): Observable<any>{
    const url = `${this.baseurl}/api/paymentParam/${paymentParamId}`;
    return this.http.get(url);
  }

  public postPayemtnParam(form:any): Observable<any>{
    const url = `${this.baseurl}/api/paymentParam`;
    return this.http.post<any>(url, form);
  }

  public updatePaymentParam(form:any): Observable<any>{
    const url = `${this.baseurl}/api/paymentParam`;
    return this.http.put<any>(url, form);
  }

  public deletePaymentParam(paymentParamId : number): Observable<any>{
    const url = `${this.baseurl}/api/paymentParam/${paymentParamId}`;
    return this.http.delete(url);
  }
}
