import { Injectable } from "@angular/core";
import { FinishTaskResponse, Task, TasksResponse } from "./task.model";
import { Observable, Subject, delay, map, tap } from "rxjs";
import { AuthService } from "src/app/auth/auth.service";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environment/environment";
import { HabitDto } from "./habit.model";
import { HeaderService } from "../header/header.service";

@Injectable({providedIn: 'root'})
export class TasksService {

    newLevelSubject = new Subject<number>();

    constructor(private authService: AuthService, private http: HttpClient,
        private headerService: HeaderService) {

    }

    fetchTasks() {
        const params = {'email': this.authService.getEmail()};
        return this.http.get<any>(environment.backendPath + 'api/task', {params: params})
            .pipe(map(response => {
                response.tasks.forEach((task: Task) => {
                    task.deadline = new Date(task.deadline);
                    console.log(task);
                });
                return response;
            }));
    }

    finishTask(taskId: number) {
        const body = {'taskId': taskId};

        return this.http.post(environment.backendPath + 'api/task/finish', body)
            .pipe(tap((response: FinishTaskResponse) => {
                if(response.leveledUp)
                    this.newLevelSubject.next(response.currentLevel);
                this.headerService.updateHeader$.next();
            }));
    }

    addTask(body) {
        const taskBody = {
            ...body,
            email: this.authService.getEmail()
        }

        return this.http.post(environment.backendPath + 'api/task/add', taskBody);
    }

    addHabit(body) {
        body = {
            ...body, email: this.authService.getEmail()
        }
        return this.http.post(environment.backendPath + 'api/habit/add', body);
    }

    fetchHabits() {
        const params = {'email': this.authService.getEmail()};
        return this.http.get<HabitDto[]>(environment.backendPath + 'api/habit', {params: params});
    }

    doHabit(habitId: number) {
        const body = {'habitId': habitId};
        return this.http.post(environment.backendPath + 'api/habit/do', body)
            .pipe(tap(() => {
                this.headerService.updateHeader$.next();
            }));
    }
}