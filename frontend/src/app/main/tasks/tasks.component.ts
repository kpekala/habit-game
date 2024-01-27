import { Component, OnDestroy, OnInit } from '@angular/core';
import { TasksService } from './tasks.service';
import { Task, TaskType } from './task.model';
import { Subscription } from 'rxjs';
import { HabitDto } from './habit.model';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit, OnDestroy{

  tasks: Task[];
  habits: HabitDto[];

  isTaskChosen = false;
  chosenTaskType?: TaskType;
  chosenTaskIndex = 0;

  creatingTask = false;

  showNewLevelCard = false;
  newlvl = -1;

  private subscription: Subscription;

  constructor (private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.reloadTasks();

    this.subscription = this.tasksService.newLevelSubject.subscribe({
      next: (level) => {
        this.newlvl = level;
        this.showNewLevelCard = true;
      }
    });
  }

  reloadTasks() {
    this.tasksService.fetchTasks().subscribe({
      next: (tasksResponse) => {
        this.tasks = tasksResponse.tasks;
      }
    });
    this.tasksService.fetchHabits().subscribe({
      next: (response) => {
        this.habits = response;
      }
    });
  }

  onTaskClicked(index: number) {
    this.isTaskChosen = true;
    this.chosenTaskType = TaskType.TASK;
    this.chosenTaskIndex = index;
  }

  onHabitClicked(index: number) {
    this.isTaskChosen = true;
    this.chosenTaskType = TaskType.HABIT;
    this.chosenTaskIndex = index;
  }

  onCloseModal(isTaskFinished = false) {
    this.isTaskChosen = false;
    if (isTaskFinished) {
      if(this.chosenTaskType === TaskType.TASK)
        this.tasks.splice(this.chosenTaskIndex, 1);
      else {
        this.habits.splice(this.chosenTaskIndex, 1);
      }
    }
  }

  onAddTaskClick(){
    this.creatingTask = true;
  }

  onCloseAddTaskModal(isTaskCreated = false) {
    this.creatingTask = false;
    this.reloadTasks();
  }

  ngOnDestroy(): void {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  onCloseNewLevelCard() {
    this.showNewLevelCard = false;
  }
}
