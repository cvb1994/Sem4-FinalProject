import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  private baseurl = environment.apiUrl;

  public getPayments(form:any): Observable<any>{
    const url = `${this.baseurl}/api/payment/list`;
    return this.http.post<any>(url, form);
  }
}
