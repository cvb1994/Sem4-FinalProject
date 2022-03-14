import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ListenCountService {

  constructor(private http: HttpClient) { }

  private baseurl = environment.apiUrl;

  public getList(form:any): Observable<any>{
    const url = `${this.baseurl}/api/listen-count/list`;
    return this.http.post<any>(url, form);
  }

  public getById(countId:any): Observable<any>{
    const url = `${this.baseurl}/api/listen-count/${countId}`;
    return this.http.get(url);
  }
}
