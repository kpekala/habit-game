import { formatDate, NgFor, NgIf } from '@angular/common';
import { Component, DestroyRef, ElementRef, EventEmitter, HostListener, input, Output, signal, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TasksService } from '../tasks.service';
import { LoadingCircleComponent } from '../../../utils/loading-circle/loading-circle.component';
import { SnackbarService } from 'src/app/utils/snackbar/snackbar.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs';

@Component({
    selector: 'app-add-task',
    templateUrl: './add-task.component.html',
    styleUrls: ['./add-task.component.scss'],
    standalone: true,
    imports: [ReactiveFormsModule, NgFor, NgIf, LoadingCircleComponent]
})
export class AddTaskComponent {
  public readonly DIFFICULTY_OPTIONS = ['EASY', 'MEDIUM', 'HARD'];
  public readonly DEFAULT_DIFFICULTY = 'EASY';

  public isTask = input.required<boolean>();
  public isLoading = false;
  public taskForm: FormGroup;

  @ViewChild('card') card: ElementRef;
  @Output() onClose = new EventEmitter<boolean>();

  constructor(private readonly tasksService: TasksService, private readonly snackbarService: SnackbarService, 
    private readonly destroyRef: DestroyRef
  ) {}

  ngOnInit(): void {
    this.taskForm = new FormGroup({
      'title': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'difficulty': new FormControl('EASY', Validators.required),
    });
    if(!this.isTask()) {
      this.taskForm.addControl('isGood', new FormControl(true, Validators.required));
      this.taskForm.addControl('location', '');
    }else {
      this.taskForm.addControl('deadline', new FormControl(formatDate(Date.now(), 'yyyy-MM-dd', 'en'), Validators.required));
    }
  }

  onCloseClick() {
    this.onClose.emit();
  }

  onAddTask() {
    this.isLoading = true;
    let body: object = {
      title: this.taskForm.value['title'],
      description: this.taskForm.value['description'],
      difficulty: this.taskForm.value['difficulty'],
      location: this.taskForm.value['location']
    }
    if(this.isTask()) {
      body = {...body, deadline: this.taskForm.value['deadline']};
      this.tasksService.addTask(body)
        .pipe(takeUntilDestroyed(this.destroyRef),
          finalize(() => {
            this.isLoading = false;
          }))
        .subscribe({
          next: () => {
            this.onClose.emit(true);
            this.snackbarService.success('Succesfully created task!');
          },error: (errorResponse) => {
            this.snackbarService.error('Cannot create task');
          }
        }
      );
    } else {
      body = {...body, isGood: this.taskForm.value['isGood']};
      this.tasksService.addHabit(body)
        .pipe(takeUntilDestroyed(this.destroyRef), 
          finalize(() => {
            this.isLoading = false;
          }))
        .subscribe({
          next: () => {
            this.onClose.emit(true);
            this.snackbarService.success('Succesfully created habit!');
          },error: () => {
            this.snackbarService.error('Cannot create habit');
          }
      });
    }
  }

  public taskLabel() {
    return this.isTask() ? 'Task' : 'Habit';
  }
 }
