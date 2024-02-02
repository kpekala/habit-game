import { Component, ElementRef, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FinishTaskResponse, Task, TaskType, TasksResponse } from '../task.model';
import { TasksService } from '../tasks.service';
import { HabitDto } from '../habit.model';

@Component({
  selector: 'app-task-modal',
  templateUrl: './task-modal.component.html',
  styleUrls: ['./task-modal.component.scss']
})
export class TaskModalComponent{

  @Output() onClose = new EventEmitter<boolean>();
  @Input() task: any;
  @Input() type: TaskType;
  isLoading = false;

  constructor(private taskService: TasksService) {}

  onCloseClick() {
    this.onClose.emit();
  }

  onFinishClick() {
    this.isLoading = true;
    if(this.type === TaskType.TASK) {
      this.taskService.finishTask(this.task.id)
      .subscribe({
        next: (response: FinishTaskResponse) => {
          this.isLoading = false;
          this.onClose.emit(true);
        },
        error: msg => {
          this.isLoading = false;
          this.onClose.emit();
        }
      });
    }else {
      this.taskService.doHabit(this.task.id)
        .subscribe({
          next: (response: FinishTaskResponse) => {
            this.isLoading = false;
            this.onClose.emit(true);
          },
          error: msg => {
            this.isLoading = false;
            this.onClose.emit();
          }
        });
    }
    
  }

  isHabit() {
    return this.type === TaskType.HABIT;
  }
}
