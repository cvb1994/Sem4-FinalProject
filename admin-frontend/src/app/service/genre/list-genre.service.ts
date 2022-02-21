import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListGenreService {

  constructor(private http: HttpClient) { }
  private baseurl = 'http://localhost:8080';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    }),
  };

  public getAllGenre(): Observable<any>{
    const url = `${this.baseurl}/api/genre`;
    return this.http.get(url, this.httpOptions);
  }
}
