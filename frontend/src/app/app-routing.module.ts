import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { MainComponent } from './main/main.component';
import { canActivateAuthContent } from './auth/auth-guard';
import { PlayerComponent } from './main/player/player.component';
import { TasksComponent } from './main/tasks/tasks.component';

const routes: Routes = [
  {path: 'auth', component: AuthComponent},
  {
    path: 'main', component: MainComponent, 
    canActivate: [canActivateAuthContent],
    children: [
      {path: 'player', component: PlayerComponent},
      {path: 'tasks', component: TasksComponent},
      {path: '**', redirectTo: 'player'}
    ]
  },
  {path: '**', redirectTo: 'auth'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
