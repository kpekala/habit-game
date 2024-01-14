import { Injectable } from "@angular/core";
import { Task, TasksResponse } from "./task.model";
import { Observable, map } from "rxjs";
import { AuthService } from "src/app/auth/auth.service";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environment/environment";

@Injectable({providedIn: 'root'})
export class TasksService {

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
            }))
    }

    finishTask(taskId: string) {
        const body = {'taskId': taskId};

        return this.http.post(environment.backendPath + 'api/task/finish', body)
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
}