import { formatDate } from '@angular/common';
import { Component, ElementRef, EventEmitter, HostListener, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TasksService } from '../tasks.service';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.scss']
})
export class AddTaskComponent {

  @ViewChild('card') card: ElementRef;
  @Output() onClose = new EventEmitter<boolean>();

  readyForClickOutside = false;
  isLoading = false;

  taskForm: FormGroup;
  difficultyOptions = ['EASY', 'MEDIUM', 'HARD'];
  defaultDifficulty = 'EASY';

  constructor(private tasksService: TasksService) {}

  @HostListener('document:click', ['$event'])
  clickOut(event) {
    if(!this.card.nativeElement.contains(event.target)) {
      // this is workaround for the fact that without this "if" this listener will register the click 
      // which opened this modal.
      if(this.readyForClickOutside) {
        this.onClose.emit();
      }
    }
  }

  ngOnInit(): void {
    setTimeout(() => {this.readyForClickOutside = true}, 100);

    this.taskForm = new FormGroup({
      'title': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'difficulty': new FormControl('EASY', Validators.required),
      'deadline': new FormControl(formatDate(Date.now(), 'yyyy-MM-dd', 'en'), Validators.required)
    });
  }

  onCloseClick() {
    this.onClose.emit();
  }

  onAddTask() {
    this.isLoading = true;
    this.tasksService.addTask(this.taskForm.value['title'], this.taskForm.value['description'],
      this.taskForm.value['deadline'], this.taskForm.value['difficulty'])
          .subscribe({
            next: (response) => {
              this.onClose.emit();
              this.isLoading = false;
              console.log('creating task successful');
            },error: (errorResponse) => {
              console.log(errorResponse);
              this.isLoading = false;
            }
          });
  }
 }
