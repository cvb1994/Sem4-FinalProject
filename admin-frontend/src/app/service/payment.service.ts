import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  private baseurl = 'http://localhost:8080';

  public getPayments(form:any): Observable<any>{
    const url = `${this.baseurl}/api/payment/list`;
    return this.http.post<any>(url, form);
  }
}
