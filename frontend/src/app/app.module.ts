import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './utils/button/button.component';
import {HttpClientModule} from '@angular/common/http';
import { MainComponent } from './main/main.component';
import { HeaderComponent } from './main/header/header.component';
import { PlayerComponent } from './main/player/player.component';
import { TasksComponent } from './main/tasks/tasks.component';
import { TaskModalComponent } from './main/tasks/task-modal/task-modal.component';
import { LoadingCircleComponent } from './utils/loading-circle/loading-circle.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    ButtonComponent,
    MainComponent,
    HeaderComponent,
    PlayerComponent,
    TasksComponent,
    TaskModalComponent,
    LoadingCircleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
