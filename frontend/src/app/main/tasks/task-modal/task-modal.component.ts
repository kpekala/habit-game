import { Component, ElementRef, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { Task, TasksResponse } from '../task.model';
import { TasksService } from '../tasks.service';

@Component({
  selector: 'app-task-modal',
  templateUrl: './task-modal.component.html',
  styleUrls: ['./task-modal.component.scss']
})
export class TaskModalComponent implements OnInit{

  @Output() onClose = new EventEmitter<boolean>();
  @Input() task: Task;
  
  @ViewChild('card') card: ElementRef;

  readyForClickOutside = false;
  isLoading = false;

  constructor(private taskService: TasksService) {}

  @HostListener('document:click', ['$event'])
  clickOut(event) {
    if(!this.card.nativeElement.contains(event.target)) {
      // this is workaround for the fact that without this "if" this listener will register the click 
      // which opened this modal.
      if(this.readyForClickOutside) {
        this.onClose.emit();
        console.log('outside');
      }
    }
  }

  ngOnInit(): void {
    console.log(typeof this.task.deadline)
    setTimeout(() => {this.readyForClickOutside = true}, 100);
  }

  onCloseClick() {
    this.onClose.emit();
  }

  onFinishClick() {
    this.isLoading = true;
    this.taskService.finishTask(this.task.id)
      .subscribe({
        next: response => {
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
