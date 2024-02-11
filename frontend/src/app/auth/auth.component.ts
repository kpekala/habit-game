import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './auth.service';
import { AuthResponse } from './auth-response.model';
import { Router } from '@angular/router';
import { ButtonComponent } from '../utils/button/button.component';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.scss'],
    standalone: true,
    imports: [ReactiveFormsModule, NgIf, ButtonComponent]
})
export class AuthComponent implements OnInit {
  
  authForm: FormGroup;
  isLoginView = true;
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {

  }
  
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
    const email = this.authForm.get('email').value;
    const password = this.authForm.get('password').value;
    this.authService.signin(email, password).subscribe({
      next: (response: AuthResponse) => {
        this.isLoading = false;
        this.router.navigate(['./main']);
      },
      error: (error) => {
        this.isLoading = false;
      }
    })
  }

  onRegisterButtonClicked() {
    const email = this.authForm.get('email').value;
    const name = this.authForm.get('name').value;
    const password = this.authForm.get('password').value;
    this.authService.signup(email, name, password).subscribe({
      next: (response: AuthResponse) => {
        this.isLoading = false;
        this.router.navigate(['./main']);
      },
      error: (error) => {
        this.isLoading = false;
      }
    })
  }

  changeMethod() {
    this.isLoginView = !this.isLoginView;
    this.reloadForm();
  }

}
