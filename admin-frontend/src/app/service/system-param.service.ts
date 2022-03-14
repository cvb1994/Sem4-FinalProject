import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SystemParamService {
  public paramChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) { 
    this.paramChanged = new Subject<boolean>();
    this.message = new Subject<string>();
  }

  private baseurl = environment.apiUrl;

   public getParams(form:any): Observable<any>{
    const url = `${this.baseurl}/api/systemParam/list`;
    return this.http.post<any>(url, form);
  }

  public postParam(form:any): Observable<any>{
    const url = `${this.baseurl}/api/systemParam`;
    return this.http.post<any>(url, form);
  }

  public updataParam(form:any): Observable<any>{
    const url = `${this.baseurl}/api/systemParam`;
    return this.http.put<any>(url, form);
  }

  public getParamById(paramId:any): Observable<any>{
    const url = `${this.baseurl}/api/systemParam/${paramId}`;
    return this.http.get(url);
  }

  public deleteParam(paramId : number): Observable<any>{
    const url = `${this.baseurl}/api/systemParam/${paramId}`;
    return this.http.delete(url);
  }
}
