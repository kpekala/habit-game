import { Injectable } from "@angular/core";
import { Task, TasksResponse } from "./task.model";
import { Observable } from "rxjs";
import { AuthService } from "src/app/auth/auth.service";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environment/environment";

@Injectable({providedIn: 'root'})
export class TasksService {

    constructor(private authService: AuthService, private http: HttpClient) {

        }

    fetchTasks(): Observable<TasksResponse> {
        const token = this.authService.getTokenHeader();
        const headers = {'Authorization': token};
        const params = {'email': this.authService.getEmail()};
        return this.http.get<TasksResponse>(environment.backendPath + 'api/task', {headers: headers, params: params});
    }
}