import { Component } from '@angular/core';
import { LoginService } from './service/login.service';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { UtilitiesService } from './service/utilities.service';
import Swal from 'sweetalert2'
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'admin-frontend';
  isLogin:any;
  private sessionStorage: Storage = sessionStorage;
  public username: any;
  public jwt :any;

  loginForm = new FormGroup({
    username: new FormControl('',Validators.required),
    password: new FormControl('',Validators.required)
  });

  constructor(
    private utilSer : UtilitiesService,
    private loginService : LoginService,
    private spinner: NgxSpinnerService
  ){}

  ngOnInit(){
    this.utilSer.message.subscribe((data:string) =>{
      if(data != "" || data != null){
        this.simpleAlert(data);
      }
    });
    let jwt = JSON.parse(this.sessionStorage.getItem('jwt')!);
    let username = JSON.parse(this.sessionStorage.getItem('username')!);
    this.jwt = jwt.value;
    this.username = username.value;
    if(jwt == null){
      this.isLogin = false;
    } else {
      this.isLogin = true;
    }
  }

  onSubmit(){
    this.spinner.show();
    const formData = new FormData();
    formData.append('username', this.loginForm.get("username")?.value);
    formData.append('password', this.loginForm.get("password")?.value);
    this.loginService.login(formData).subscribe({
      next : (data) =>{
        this.spinner.hide();
        if(data.status == true){
          var jwt = {value:data.content.jwt};
          var username = {value:data.content.username};
          this.sessionStorage.setItem('jwt', JSON.stringify(jwt));
          this.sessionStorage.setItem('username', JSON.stringify(username));
          window.location.reload();
        } else {
          this.simpleAlert(data.message);
        }
      },
      error: (err)=>{
        this.spinner.hide();
        this.simpleAlert("Không thể kết nối đến server.");
      }
    })
  }

  logOut(){
    this.sessionStorage.removeItem('jwt');
    this.sessionStorage.removeItem('username');
    window.location.reload();
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }
}
