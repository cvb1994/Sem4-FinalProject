import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  public genreChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) {
    this.genreChanged = new Subject<boolean>();
    this.message = new Subject<string>();
  }
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

  public getGenreByPage(form:any): Observable<any>{
    const url = `${this.baseurl}/api/genre/list`;
    return this.http.post<any>(url, form);
  }

  public getGenreById(genreId:any): Observable<any>{
    const url = `${this.baseurl}/api/genre/${genreId}`;
    return this.http.get(url, this.httpOptions);
  }

  public postGenre(form:any): Observable<any>{
    const url = `${this.baseurl}/api/genre`;
    return this.http.post<any>(url, form);
  }

  public updateGenre(form:any): Observable<any>{
    const url = `${this.baseurl}/api/genre`;
    return this.http.put<any>(url, form);
  }

  public deleteGenre(genreId : number): Observable<any>{
    const url = `${this.baseurl}/api/genre/${genreId}`;
    return this.http.delete(url, this.httpOptions);
  }

  public getGenresOrderByName(): Observable<any>{
    const url = `${this.baseurl}/api/genre/getAll`;
    return this.http.get(url, this.httpOptions);
  }
}
