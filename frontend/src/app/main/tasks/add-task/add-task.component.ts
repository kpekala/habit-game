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

  isLoading = false;

  taskForm: FormGroup;
  difficultyOptions = ['EASY', 'MEDIUM', 'HARD'];
  defaultDifficulty = 'EASY';

  constructor(private tasksService: TasksService) {}

  ngOnInit(): void {
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
              this.onClose.emit(true);
              this.isLoading = false;
              console.log('creating task successful');
            },error: (errorResponse) => {
              console.log(errorResponse);
              this.isLoading = false;
            }
          });
  }
 }
