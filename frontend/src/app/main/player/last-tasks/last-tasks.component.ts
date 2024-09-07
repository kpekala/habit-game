import { Component, OnInit } from '@angular/core';
import { TasksService } from '../../tasks/tasks.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-last-tasks',
  templateUrl: './last-tasks.component.html',
  styleUrls: ['./last-tasks.component.scss'],
  standalone: true,
  imports: [NgFor],
})
export class LastTasksComponent implements OnInit {
  tasks = [];

  constructor(private tasksService: TasksService) {}

  ngOnInit(): void {
    this.tasksService.fetchCompletedTasks().subscribe({
      next: (response) => {
        this.tasks = response.tasks;
      },
      error: (errorMsg) => {},
    });
  }
}
