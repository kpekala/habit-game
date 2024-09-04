import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, map, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { environment } from 'src/environments/environment';
import { UserService } from '../player/user.service';
import { HabitDto } from './habit.model';
import { FinishTaskResponse, Task } from './task.model';

@Injectable({ providedIn: 'root' })
export class TasksService {
  newLevelSubject = new Subject<number>();

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private userService: UserService
  ) {}

  fetchTasks() {
    const params = { email: this.authService.getEmail() };
    return this.http
      .get<any>(environment.backendPath + 'api/task', { params: params })
      .pipe(
        map((response) => {
          response.tasks.forEach((task: Task) => {
            task.deadline = new Date(task.deadline);
          });
          return response;
        })
      );
  }

  finishTask(taskId: number) {
    const body = { taskId: taskId };

    return this.http
      .post(environment.backendPath + 'api/task/finish', body)
      .pipe(
        tap((response: FinishTaskResponse) => {
          if (response.leveledUp)
            this.newLevelSubject.next(response.currentLevel);
          this.userService.fetchUserInformation().subscribe();
        })
      );
  }

  addTask(body) {
    const taskBody = {
      ...body,
      email: this.authService.getEmail(),
    };

    return this.http.post(environment.backendPath + 'api/task/add', taskBody);
  }

  addHabit(body) {
    body = {
      ...body,
      email: this.authService.getEmail(),
    };
    return this.http.post(environment.backendPath + 'api/habit/add', body);
  }

  fetchHabits() {
    const params = { email: this.authService.getEmail() };
    return this.http.get<HabitDto[]>(environment.backendPath + 'api/habit', {
      params: params,
    });
  }

  doHabit(habitId: number) {
    const body = { habitId: habitId };
    return this.http.post(environment.backendPath + 'api/habit/do', body).pipe(
      tap(() => {
        this.userService.fetchUserInformation().subscribe();
      })
    );
  }
}
