import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

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

  public getGenreById(genreId:any): Observable<any>{
    const url = `${this.baseurl}/api/genre/${genreId}`;
    return this.http.get(url, this.httpOptions);
  }

  public postGenre(form:any){
    const url = `${this.baseurl}/api/genre`;
    this.http.post<any>(url, form).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }

  public updateGenre(form:any){
    const url = `${this.baseurl}/api/genre`;
    this.http.put<any>(url, form).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }
}
