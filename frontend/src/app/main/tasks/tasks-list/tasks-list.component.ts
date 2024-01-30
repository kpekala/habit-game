import { Component, Input, OnInit } from '@angular/core';
import { TasksService } from '../tasks.service';
import { Task, TaskType } from '../task.model';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent implements OnInit{
  
  @Input() taskType: TaskType;

  tasks: any[]
  isTaskChosen = false;
  chosenTaskIndex = 0;
  creatingTask = false;

  constructor(private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.reloadTasks();
  }

  reloadTasks() {
    const tasks$ = this.taskType === TaskType.TASK ? this.tasksService.fetchTasks() : this.tasksService.fetchHabits();

    tasks$.subscribe({
      next: (tasksResponse) => {
        this.tasks = this.taskType === TaskType.TASK ? tasksResponse.tasks : tasksResponse;
      }
    });
  }

  onCloseModal(isTaskFinished = false) {
    this.isTaskChosen = false;
    if (isTaskFinished && this.taskType === TaskType.TASK) {
        this.tasks.splice(this.chosenTaskIndex, 1);
    }
  }

  onAddTaskClick(){
    this.creatingTask = true;
  }

  onCloseAddTaskModal(isTaskCreated = false) {
    this.creatingTask = false;
  }

  onTaskClicked(index: number) {
      this.chosenTaskIndex = index;
      this.isTaskChosen = true;
  }
}
