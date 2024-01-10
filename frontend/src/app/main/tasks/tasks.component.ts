import { Component, OnInit } from '@angular/core';
import { TasksService } from './tasks.service';
import { Task } from './task.model';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit{

  tasks: Task[];

  constructor (private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.tasksService.fetchTasks().subscribe({
      next: (tasksResponse) => {
        this.tasks = tasksResponse.tasks;
        console.log(this.tasks);
      }
    });
  }
}
