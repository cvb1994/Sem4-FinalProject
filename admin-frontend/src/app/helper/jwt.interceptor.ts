import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    private sessionStorage: Storage = sessionStorage;
    private jwt : any;

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.jwt = JSON.parse(this.sessionStorage.getItem('jwt')!);
        if(this.jwt != null && this.jwt != ""){
            req = req.clone({
                setHeaders: { Authorization: `Bearer ${this.jwt.value}` }
            });
        }
        return next.handle(req);
    }
    
}