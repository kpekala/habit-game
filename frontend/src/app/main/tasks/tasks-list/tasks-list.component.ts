import { Component, DestroyRef, input, Input, OnInit } from '@angular/core';
import { TasksService } from '../tasks.service';
import { Task, TaskType } from '../task.model';
import { AddTaskComponent } from '../add-task/add-task.component';
import { TaskModalComponent } from '../task-modal/task-modal.component';
import { CardComponent } from '../../../utils/card/card.component';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
    selector: 'app-tasks-list',
    templateUrl: './tasks-list.component.html',
    styleUrls: ['./tasks-list.component.scss'],
    standalone: true,
    imports: [NgIf, NgFor, NgClass, CardComponent, TaskModalComponent, AddTaskComponent]
})
export class TasksListComponent implements OnInit{
  
  public taskType = input.required<number>();
  tasks: any[]
  isTaskChosen = false;
  chosenTaskIndex = 0;
  creatingTask = false;
  isDead = false;

  constructor(private tasksService: TasksService, private destroyRef: DestroyRef) {}

  ngOnInit(): void {
    this.reloadTasks();
  }

  reloadTasks() {
    const tasks$ = this.taskType() === TaskType.TASK ? this.tasksService.fetchTasks() : this.tasksService.fetchHabits();

    tasks$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
      next: (tasksResponse) => {
        this.tasks = this.taskType() === TaskType.TASK ? tasksResponse.tasks : tasksResponse;
      }
    });
  }

  onCloseModal({isTaskFinished = false, isDead = false}) {
    this.isTaskChosen = false;
    if (isTaskFinished && this.taskType() === TaskType.TASK) {
        this.tasks.splice(this.chosenTaskIndex, 1);
    }
    this.isDead = isDead;
  }

  onAddTaskClick(){
    this.creatingTask = true;
  }

  onCloseAddTaskModal(isTaskCreated = false) {
    this.creatingTask = false;
    if(isTaskCreated){
      this.reloadTasks();
    }
  }

  onTaskClicked(index: number) {
      this.chosenTaskIndex = index;
      this.isTaskChosen = true;
  }

  onCloseDeadModal() {
    this.isDead = false;
  }
}
