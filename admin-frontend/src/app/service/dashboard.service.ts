import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  private baseurl = environment.apiUrl;

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

  public getGenreReport():Observable<any>{
    const url = `${this.baseurl}/api/genre/getReport`;
    return this.http.get<any>(url);
  }
}
