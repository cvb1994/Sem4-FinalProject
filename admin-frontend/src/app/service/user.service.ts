import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public userChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) { 
    this.userChanged = new Subject<boolean>();
    this.message = new Subject<string>();
  }

  private baseurl = environment.apiUrl;

  public getUsers(form:any): Observable<any>{
    const url = `${this.baseurl}/api/user/list`;
    return this.http.post<any>(url, form);
  }

  public deleteUser(userId : number): Observable<any>{
    const url = `${this.baseurl}/api/user/${userId}`;
    return this.http.delete(url);
  }
}
