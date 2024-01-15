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

  isTaskChosen = false;
  chosenTaskIndex = 0;

  creatingTask = false;

  constructor (private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.reloadTasks();
  }

  reloadTasks() {
    this.tasksService.fetchTasks().subscribe({
      next: (tasksResponse) => {
        this.tasks = tasksResponse.tasks;
      }
    });
  }

  onTaskClicked(index: number) {
    this.isTaskChosen = true;
    this.chosenTaskIndex = index;
  }

  onCloseModal(isTaskFinished = false) {
    this.isTaskChosen = false;
    if (isTaskFinished) {
      this.tasks.splice(this.chosenTaskIndex, 1);
    }
  }

  onAddTaskClick(){
    this.creatingTask = true;
  }

  onCloseAddTaskModal(isTaskCreated = false) {
    this.creatingTask = false;
    this.reloadTasks();
  }
}
