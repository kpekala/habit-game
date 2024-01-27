import { Injectable } from "@angular/core";
import { FinishTaskResponse, Task, TasksResponse } from "./task.model";
import { Observable, Subject, delay, map, tap } from "rxjs";
import { AuthService } from "src/app/auth/auth.service";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environment/environment";
import { HabitDto } from "./habit.model";

@Injectable({providedIn: 'root'})
export class TasksService {

    newLevelSubject = new Subject<number>();

    constructor(private authService: AuthService, private http: HttpClient) {

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

    finishTask(taskId: string) {
        const body = {'taskId': taskId};

        return this.http.post(environment.backendPath + 'api/task/finish', body)
            .pipe(tap((response: FinishTaskResponse) => {
                if(response.leveledUp)
                    this.newLevelSubject.next(response.currentLevel);
            }));
    }

    addTask(title: string, description: string, deadline: Date, difficulty: string) {
        const taskBody = {
            title: title,
            description: description,
            deadline: deadline,
            difficulty: difficulty,
            email: this.authService.getEmail()
        }

        return this.http.post(environment.backendPath + 'api/task/add', taskBody);
    }

    fetchHabits() {
        const params = {'email': this.authService.getEmail()};
        return this.http.get<HabitDto[]>(environment.backendPath + 'api/habit', {params: params});
    }
}