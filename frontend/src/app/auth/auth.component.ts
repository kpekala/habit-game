import {
    AfterViewInit,
    ChangeDetectorRef,
    Component,
    DestroyRef,
    OnDestroy,
    OnInit,
} from '@angular/core'
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule,
} from '@angular/forms'
import { AuthService } from './auth.service'
import { AuthResponse } from './dtos/auth'
import { Router } from '@angular/router'
import { ButtonComponent } from '../utils/ui/button/button.component'
import { NgIf } from '@angular/common'
import { finalize, Subject, takeUntil } from 'rxjs'
import { HttpErrorResponse } from '@angular/common/http'
import { takeUntilDestroyed } from '@angular/core/rxjs-interop'

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.scss'],
    standalone: true,
    imports: [ReactiveFormsModule, NgIf, ButtonComponent],
})
export class AuthComponent implements OnInit, AfterViewInit {
    authForm: FormGroup
    isLoginView = true
    isLoading = false
    errorMessage = ''
    responseEnd = ''

    constructor(
        private authService: AuthService,
        private router: Router,
        private readonly destroyRef: DestroyRef,
        private readonly cd: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.reloadForm()
        const observer = new PerformanceObserver((entryList) => {
            for (const entry of entryList.getEntries()) {
                if (entry.entryType === 'largest-contentful-paint') {
                    this.responseEnd = entry.startTime.toFixed(2)
                    console.log(
                        `Time from page start to LCP: ${this.responseEnd} ms`
                    )
                    console.log(entry)
                }
            }
            this.cd.detectChanges()
        })

        observer.observe({ type: 'largest-contentful-paint', buffered: true })
    }

    ngAfterViewInit(): void {
        const endTime = performance.now()
        this.authForm.get('email').setValue(endTime - (window as any).startTime)
    }

    getCurrentFormattedTime() {
        const now = new Date()
        const hours = now.getHours()
        const minutes = now.getMinutes()
        const seconds = now.getSeconds()
        const milliseconds = now.getMilliseconds()

        return `${this.pad(hours)}:${this.pad(minutes)}:${this.pad(
            seconds
        )}.${this.pad(milliseconds, 3)}`
    }

    pad(number, digits = 2) {
        return number.toString().padStart(digits, '0')
    }

    reloadForm() {
        this.authForm = new FormGroup({
            email: new FormControl('', Validators.required),
            password: new FormControl('', Validators.required),
        })

        this.authForm.valueChanges
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe((changes) => {
                this.errorMessage = ''
            })

        if (!this.isLoginView) {
            this.authForm.addControl(
                'name',
                new FormControl('', Validators.required)
            )
        }
    }

    onAuthButtonClicked() {
        this.isLoading = true

        if (!this.isLoginView) {
            this.onRegisterButtonClicked()
        } else {
            this.onLoginButtonClicked()
        }
    }

    onLoginButtonClicked() {
        const email = this.authForm.get('email').value
        const password = this.authForm.get('password').value
        this.authService
            .signin(email, password)
            .pipe(
                finalize(() => {
                    this.isLoading = false
                })
            )
            .subscribe({
                next: (response: AuthResponse) => {
                    this.router.navigate(['./main'])
                },
                error: (error: HttpErrorResponse) => {
                    if (error.status === 403) {
                        this.errorMessage = 'Bad credentials'
                    } else {
                        this.errorMessage = error.message
                    }
                },
            })
    }

    onRegisterButtonClicked() {
        const email = this.authForm.get('email').value
        const name = this.authForm.get('name').value
        const password = this.authForm.get('password').value
        this.authService.signup(email, name, password).subscribe({
            next: (response: AuthResponse) => {
                this.isLoading = false
                this.router.navigate(['./main'])
            },
            error: (error) => {
                this.isLoading = false
            },
        })
    }

    changeMethod() {
        this.isLoginView = !this.isLoginView
        this.reloadForm()
    }
}
