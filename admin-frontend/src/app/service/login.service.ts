import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }
  private baseurl = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    }),
  };

  public login(form:any): Observable<any>{
    const url = `${this.baseurl}/admin/login`;
    return this.http.post<any>(url, form);
  }
}
