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
        const token = this.authService.getTokenHeader();
        const headers = {'Authorization': token};
        const params = {'email': this.authService.getEmail()};
        return this.http.get<any>(environment.backendPath + 'api/task', {headers: headers, params: params})
            .pipe(map(response => {
                response.tasks.forEach((task: Task) => {
                    task.deadline = new Date(task.deadline);
                    console.log(task);
                });
                return response;
            }))
    }

    finishTask(taskId: string) {
        const token = this.authService.getTokenHeader();
        const headers = {'Authorization': token};
        const body = {'taskId': taskId};

        return this.http.post(environment.backendPath + 'api/task/finish', body, {headers: headers})
    }
}