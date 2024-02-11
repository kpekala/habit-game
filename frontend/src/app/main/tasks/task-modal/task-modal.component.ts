import { Component, ElementRef, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FinishTaskResponse, Task, TaskType, TasksResponse } from '../task.model';
import { TasksService } from '../tasks.service';
import { DoHabitResponse, HabitDto } from '../habit.model';
import { LoadingCircleComponent } from '../../../utils/loading-circle/loading-circle.component';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-task-modal',
    templateUrl: './task-modal.component.html',
    styleUrls: ['./task-modal.component.scss'],
    standalone: true,
    imports: [NgIf, LoadingCircleComponent]
})
export class TaskModalComponent{

  @Output() onClose = new EventEmitter<{isTaskFinished?: boolean; isDead?: boolean;}>();
  @Input() task: any;
  @Input() type: TaskType;
  isLoading = false;

  constructor(private taskService: TasksService) {}

  onCloseClick() {
    this.onClose.emit({});
  }

  onFinishClick() {
    this.isLoading = true;
    if(this.type === TaskType.TASK) {
      this.taskService.finishTask(this.task.id)
      .subscribe({
        next: (response: FinishTaskResponse) => {
          this.isLoading = false;
          this.onClose.emit({isTaskFinished: true});
        },
        error: msg => {
          this.isLoading = false;
          this.onClose.emit();
        }
      });
    }else {
      this.taskService.doHabit(this.task.id)
        .subscribe({
          next: (response: DoHabitResponse) => {
            this.isLoading = false;
            this.onClose.emit({isTaskFinished: true, isDead: response.dead});
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
