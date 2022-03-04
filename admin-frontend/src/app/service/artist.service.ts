import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {
  public artistChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) {
    this.artistChanged = new Subject<boolean>();
    this.message = new Subject<string>();
  }

  private baseurl = 'http://localhost:8080';

  public getArtists(form:any): Observable<any>{
    const url = `${this.baseurl}/api/artist/list`;
    return this.http.post<any>(url, form);
  }

  public postArtist(form:any): Observable<any>{
    const url = `${this.baseurl}/api/artist`;
    return this.http.post<any>(url, form);
  }

  public updateArtist(form:any): Observable<any>{
    const url = `${this.baseurl}/api/artist`;
    return this.http.put<any>(url, form);
  }

  public getArtistById(artistId:any): Observable<any>{
    const url = `${this.baseurl}/api/artist/${artistId}`;
    return this.http.get(url);
  }

  public deleteArtist(artistId : number): Observable<any>{
    const url = `${this.baseurl}/api/artist/${artistId}`;
    return this.http.delete(url);
  }

  public getArtistsOrderByName(): Observable<any>{
    const url = `${this.baseurl}/api/artist/getAll`;
    return this.http.get<any>(url);
  }


}
