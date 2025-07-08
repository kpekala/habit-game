import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


import { importProvidersFrom, isDevMode } from '@angular/core';
import { AppComponent } from './app/app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app/app-routing.module';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { AuthInterceptorService } from './app/auth/auth.interceptor.service';
import { HTTP_INTERCEPTORS, withInterceptorsFromDi, provideHttpClient } from '@angular/common/http';
import { provideServiceWorker } from '@angular/service-worker';


bootstrapApplication(AppComponent, {
    providers: [
    importProvidersFrom(BrowserModule, AppRoutingModule, ReactiveFormsModule),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true },
    provideHttpClient(withInterceptorsFromDi()),
    provideServiceWorker('ngsw-worker.js', {
        enabled: !isDevMode(),
        registrationStrategy: 'registerWhenStable:30000'
    })
]
})
  .catch(err => console.error(err));
