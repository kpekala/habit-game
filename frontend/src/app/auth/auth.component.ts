import { Component, DestroyRef, OnDestroy, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { AuthService } from './auth.service';
import { AuthResponse } from './dtos/auth';
import { Router } from '@angular/router';
import { ButtonComponent } from '../utils/ui/button/button.component';
import { NgIf } from '@angular/common';
import { finalize, Subject, takeUntil } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, ButtonComponent],
})
export class AuthComponent implements OnInit {
  authForm: FormGroup;
  isLoginView = true;
  isLoading = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router, private readonly destroyRef: DestroyRef) {}

  ngOnInit(): void {
    this.reloadForm();
  }

  reloadForm() {
    this.authForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });

    this.authForm.valueChanges.pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((changes => {
        this.errorMessage = '';
      }))

    if (!this.isLoginView) {
      this.authForm.addControl(
        'name',
        new FormControl('', Validators.required)
      );
    }
  }

  onAuthButtonClicked() {
    this.isLoading = true;

    if (!this.isLoginView) {
      this.onRegisterButtonClicked();
    } else {
      this.onLoginButtonClicked();
    }
  }

  onLoginButtonClicked() {
    const email = this.authForm.get('email').value;
    const password = this.authForm.get('password').value;
    this.authService.signin(email, password)
      .pipe(finalize(() => {
        this.isLoading = false;
      }))
      .subscribe({
      next: (response: AuthResponse) => {
        this.router.navigate(['./main']);
      },
      error: (error: HttpErrorResponse) => {
        if(error.status === 403) {
          this.errorMessage = 'Bad credentials';
        } else {
          this.errorMessage = error.message;
        }
      },
    });
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
      },
    });
  }

  changeMethod() {
    this.isLoginView = !this.isLoginView;
    this.reloadForm();
  }
}
