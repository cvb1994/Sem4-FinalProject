import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  private baseurl = environment.apiUrl;

  public getUserById(userId:number): Observable<any>{
    const url = `${this.baseurl}/api/user/${userId}`;
    return this.http.get(url);
  }
}
