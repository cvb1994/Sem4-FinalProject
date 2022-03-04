import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  public songChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) {
    this.songChanged = new Subject<boolean>();
    this.message = new Subject<string>();
  }

  private baseurl = 'http://localhost:8080';

   public getSongs(form:any): Observable<any>{
    const url = `${this.baseurl}/api/song/list`;
    return this.http.post<any>(url, form);
  }

  public postSong(form:any): Observable<any>{
    const url = `${this.baseurl}/api/song`;
    return this.http.post<any>(url, form);
  }

  public updateSong(form:any): Observable<any>{
    const url = `${this.baseurl}/api/song`;
    return this.http.put<any>(url, form);
  }

  public getSongById(songId:any): Observable<any>{
    const url = `${this.baseurl}/api/song/${songId}`;
    return this.http.get(url);
  }

  public deleteSong(songId : number): Observable<any>{
    const url = `${this.baseurl}/api/song/${songId}`;
    return this.http.delete(url);
  }
}
