import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {
  
  authForm: FormGroup;
  isLoginView = true;
  isLoading = false;
  
  ngOnInit(): void {
    this.reloadForm();
  }

  reloadForm(){
    this.authForm = new FormGroup({
      'email': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
    });

    if(!this.isLoginView) {
      this.authForm.addControl('name', new FormControl('', Validators.required));
    }
  }

  onAuthButtonClicked(){
    this.isLoading = true;

    if(!this.isLoginView){
      this.onRegisterButtonClicked();
    }else {
      this.onLoginButtonClicked();
    }
  }

  onLoginButtonClicked() {
  }

  onRegisterButtonClicked() {
  }

  changeMethod() {
    this.isLoginView = !this.isLoginView;
    this.reloadForm();
  }

}
