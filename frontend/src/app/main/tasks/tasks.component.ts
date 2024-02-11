import { Component, OnDestroy, OnInit } from '@angular/core';
import { TasksService } from './tasks.service';
import { Task, TaskType } from './task.model';
import { Subscription } from 'rxjs';
import { HabitDto } from './habit.model';
import { NewLevelComponent } from './new-level/new-level.component';
import { CardComponent } from '../../utils/card/card.component';
import { NgIf } from '@angular/common';
import { TasksListComponent } from './tasks-list/tasks-list.component';

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.scss'],
    standalone: true,
    imports: [TasksListComponent, NgIf, CardComponent, NewLevelComponent]
})
export class TasksComponent implements OnInit, OnDestroy{

  showNewLevelCard = false;
  newlvl = -1;

  private subscription: Subscription;

  constructor (private tasksService: TasksService) {

  }

  ngOnInit(): void {
    this.subscription = this.tasksService.newLevelSubject.subscribe({
      next: (level) => {
        this.newlvl = level;
        this.showNewLevelCard = true;
      }
    });
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
