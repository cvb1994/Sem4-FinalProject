import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  private baseurl = 'http://localhost:8080';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    }),
  };

  public getDashboard(): Observable<any>{
    const url = `${this.baseurl}/api/v1/admin/dashboard`;
    return this.http.get<any>(url);
  }

  public getProfitRePort(amount:number):Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("monthAmount",amount);
    
    const url = `${this.baseurl}/api/v1/admin/profit-report`;
    return this.http.get<any>(url, {params:queryParams});
  }
}
