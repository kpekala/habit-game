import { Component, ElementRef, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FinishTaskResponse, Task, TasksResponse } from '../task.model';
import { TasksService } from '../tasks.service';

@Component({
  selector: 'app-task-modal',
  templateUrl: './task-modal.component.html',
  styleUrls: ['./task-modal.component.scss']
})
export class TaskModalComponent{

  @Output() onClose = new EventEmitter<boolean>();
  @Input() task: Task;
  
  isLoading = false;

  constructor(private taskService: TasksService) {}

  onCloseClick() {
    this.onClose.emit();
  }

  onFinishClick() {
    this.isLoading = true;
    this.taskService.finishTask(this.task.id)
      .subscribe({
        next: (response: FinishTaskResponse) => {
          this.isLoading = false;
          this.onClose.emit(true);
        },
        error: msg => {
          console.log(msg);
          this.isLoading = false;
          this.onClose.emit();
        }
      })
  }
}
