import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './utils/button/button.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { MainComponent } from './main/main.component';
import { HeaderComponent } from './main/header/header.component';
import { PlayerComponent } from './main/player/player.component';
import { TasksComponent } from './main/tasks/tasks.component';
import { TaskModalComponent } from './main/tasks/task-modal/task-modal.component';
import { LoadingCircleComponent } from './utils/loading-circle/loading-circle.component';
import { AuthInterceptorService } from './auth/auth.interceptor.service';
import { AddTaskComponent } from './main/tasks/add-task/add-task.component';
import { NewLevelComponent } from './main/tasks/new-level/new-level.component';
import { CardComponent } from './utils/card/card.component';
import { LastTasksComponent } from './main/player/last-tasks/last-tasks.component';

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
    LoadingCircleComponent,
    AddTaskComponent,
    NewLevelComponent,
    CardComponent,
    LastTasksComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
