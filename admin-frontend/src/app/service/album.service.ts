import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  public albumChanged: Subject<boolean>;
  public message: Subject<string>;

  constructor(private http: HttpClient) {
    this.albumChanged = new Subject<boolean>();
    this.message = new Subject<string>();
   }

   private baseurl = environment.apiUrl;
   

   public getAlbums(form:any): Observable<any>{
    const url = `${this.baseurl}/api/album/list`;
    return this.http.post<any>(url, form);
  }

  public postAlbum(form:any): Observable<any>{
    const url = `${this.baseurl}/api/album`;
    return this.http.post<any>(url, form);
  }

  public updateAlbum(form:any): Observable<any>{
    const url = `${this.baseurl}/api/album`;
    return this.http.put<any>(url, form);
  }

  public getAlbumById(albumId:any): Observable<any>{
    const url = `${this.baseurl}/api/album/${albumId}`;
    return this.http.get(url);
  }

  public deleteAlbum(albumId : number): Observable<any>{
    const url = `${this.baseurl}/api/album/${albumId}`;
    return this.http.delete(url);
  }

  public getAllOrderByName() : Observable<any>{
    const url = `${this.baseurl}/api/album/getAll`;
    return this.http.get(url);
  }

}
