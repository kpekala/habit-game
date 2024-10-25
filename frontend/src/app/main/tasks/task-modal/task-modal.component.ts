import { Component, ElementRef, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FinishTaskResponse, Task, TaskType, TasksResponse } from '../task.model';
import { TasksService } from '../tasks.service';
import { DoHabitResponse, HabitDto } from '../habit.model';
import { LoadingCircleComponent } from '../../../utils/ui/loading-circle/loading-circle.component';
import { NgIf } from '@angular/common';
import { SnackbarService } from 'src/app/utils/ui/snackbar/snackbar.service';

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

  constructor(private taskService: TasksService, private snackbarService: SnackbarService) {}

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
          this.snackbarService.success('Succesfully finished task!');
        },
        error: msg => {
          this.isLoading = false;
          this.onClose.emit();
          this.snackbarService.error('Error during finishing task');
        }
      });
    }else {
      this.taskService.doHabit(this.task.id)
        .subscribe({
          next: (response: DoHabitResponse) => {
            this.isLoading = false;
            this.onClose.emit({isTaskFinished: true, isDead: response.dead});
            this.snackbarService.success('You\'ve done a habit!');
          },
          error: msg => {
            this.isLoading = false;
            this.onClose.emit();
            this.snackbarService.error('Error during doing habit');
          }
        });
    }
    
  }

  isHabit() {
    return this.type === TaskType.HABIT;
  }
}
