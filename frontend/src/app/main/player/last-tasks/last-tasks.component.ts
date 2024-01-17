import { Component, OnInit } from '@angular/core';
import { TasksService } from '../../tasks/tasks.service';

@Component({
  selector: 'app-last-tasks',
  templateUrl: './last-tasks.component.html',
  styleUrls: ['./last-tasks.component.scss']
})
export class LastTasksComponent implements OnInit{
  
  tasks = [];

  constructor(private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.tasksService.fetchTasks().subscribe({
      next: response => {
        this.tasks = response.tasks;
      },
      error: errorMsg => {

      }
    });
  }
}
