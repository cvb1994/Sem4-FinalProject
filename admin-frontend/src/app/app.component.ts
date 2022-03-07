import { Component } from '@angular/core';
import { LoginService } from './service/login.service';
import { FormGroup, FormControl, Validators, FormBuilder  } from '@angular/forms';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'admin-frontend';
  isLogin:any;
  private localStorage: Storage = localStorage;
  public username: any;
  public jwt :any;

  loginForm = new FormGroup({
    username: new FormControl('',Validators.required),
    password: new FormControl('',Validators.required)
  });

  constructor(
    private loginService : LoginService,
    private fb: FormBuilder
  ){
    console.log(this.isLogin);
  }

  ngOnInit(){
    let jwt = JSON.parse(this.localStorage.getItem('jwt')!);
    let username = JSON.parse(this.localStorage.getItem('username')!);
    this.jwt = jwt.value;
    this.username = username.value;
    if(jwt == null){
      this.isLogin = false;
    } else {
      this.isLogin = true;
    }
  }

  onSubmit(){
    const formData = new FormData();
    formData.append('username', this.loginForm.get("username")?.value);
    formData.append('password', this.loginForm.get("password")?.value);
    console.log(this.loginForm.get("username")?.value);
    this.loginService.login(formData).subscribe((data) => {
      console.log(data);
      if(data.status == true){
        var jwt = {value:data.content.jwt};
        var username = {value:data.content.username};
        this.localStorage.setItem('jwt', JSON.stringify(jwt));
        this.localStorage.setItem('username', JSON.stringify(username));
        window.location.reload();
      } else {
        this.simpleAlert(data.message);
      }
    })
  }

  logOut(){
    this.localStorage.removeItem('jwt');
    this.localStorage.removeItem('username');
    window.location.reload();
  }

  simpleAlert(message:string){
    Swal.fire(message);
  }
}
