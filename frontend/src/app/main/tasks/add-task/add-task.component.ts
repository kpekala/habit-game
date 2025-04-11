import { formatDate, NgFor, NgIf } from '@angular/common'
import {
    Component,
    DestroyRef,
    ElementRef,
    EventEmitter,
    HostListener,
    input,
    Output,
    signal,
    ViewChild,
} from '@angular/core'
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule,
} from '@angular/forms'
import { TasksService } from '../tasks.service'
import { LoadingCircleComponent } from '../../../utils/ui/loading-circle/loading-circle.component'
import { SnackbarService } from 'src/app/utils/ui/snackbar/snackbar.service'
import { takeUntilDestroyed } from '@angular/core/rxjs-interop'
import { finalize, Observable, of, switchMap } from 'rxjs'
import { GeolocationService } from 'src/app/utils/data/geolocation.service'
import { v4 as uuidv4 } from 'uuid'

@Component({
    selector: 'app-add-task',
    templateUrl: './add-task.component.html',
    styleUrls: ['./add-task.component.scss'],
    standalone: true,
    imports: [ReactiveFormsModule, NgFor, NgIf, LoadingCircleComponent],
})
export class AddTaskComponent {
    public readonly DIFFICULTY_OPTIONS = ['EASY', 'MEDIUM', 'HARD']
    public readonly DEFAULT_DIFFICULTY = 'EASY'

    public isTask = input.required<boolean>()
    public isLoading = false
    public taskForm: FormGroup

    public selectedFile: File | null = null
    public imagePreview: string | null = null
    public photoId = ''

    @ViewChild('card') card: ElementRef
    @Output() onClose = new EventEmitter<boolean>()

    constructor(
        private readonly tasksService: TasksService,
        private readonly snackbarService: SnackbarService,
        private readonly destroyRef: DestroyRef,
        private readonly geolocationService: GeolocationService
    ) {}

    ngOnInit(): void {
        this.taskForm = new FormGroup({
            title: new FormControl('', Validators.required),
            description: new FormControl('', Validators.required),
            difficulty: new FormControl('EASY', Validators.required),
        })
        if (!this.isTask()) {
            this.taskForm.addControl(
                'isGood',
                new FormControl(true, Validators.required)
            )
        } else {
            this.taskForm.addControl('location', new FormControl(''))
            this.taskForm.addControl(
                'deadline',
                new FormControl(
                    formatDate(Date.now(), 'yyyy-MM-dd', 'en'),
                    Validators.required
                )
            )
        }
    }

    onCloseClick() {
        this.onClose.emit()
    }

    onAddTask() {
        this.isLoading = true
        let body: object = {
            title: this.taskForm.value['title'],
            description: this.taskForm.value['description'],
            difficulty: this.taskForm.value['difficulty'],
            location: this.parseLocationInput(this.taskForm.value['location']),
        }
        if (this.isTask()) {
            body = {
                ...body,
                deadline: this.taskForm.value['deadline'],
                photoId: `${this.photoId}.${
                    this.selectedFile.name.split('.')[1]
                }`,
            }
            this.tasksService
                .addTask(body)
                .pipe(
                    switchMap(() => {
                        if (this.selectedFile)
                            return this.tasksService.uploadPhoto(
                                this.selectedFile,
                                this.photoId
                            )
                        else return of()
                    }),
                    takeUntilDestroyed(this.destroyRef),
                    finalize(() => {
                        this.isLoading = false
                    })
                )
                .subscribe({
                    next: () => {
                        this.onClose.emit(true)
                        this.snackbarService.success(
                            'Succesfully created task!'
                        )
                    },
                    error: (errorResponse) => {
                        this.snackbarService.error('Cannot create task')
                    },
                })
        } else {
            body = { ...body, isGood: this.taskForm.value['isGood'] }
            this.tasksService
                .addHabit(body)
                .pipe(
                    takeUntilDestroyed(this.destroyRef),
                    finalize(() => {
                        this.isLoading = false
                    })
                )
                .subscribe({
                    next: () => {
                        this.onClose.emit(true)
                        this.snackbarService.success(
                            'Succesfully created habit!'
                        )
                    },
                    error: () => {
                        this.snackbarService.error('Cannot create habit')
                    },
                })
        }
    }

    public taskLabel() {
        return this.isTask() ? 'Task' : 'Habit'
    }

    public onClickGeolocation() {
        this.geolocationService
            .getGeolocation()
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe({
                next: (position: GeolocationPosition) => {
                    this.taskForm.controls['location'].setValue(
                        `${position.coords.latitude} ${position.coords.longitude}`
                    )
                },
                error: (error: Error) => {},
            })
    }

    public onFileSelected(event: Event): void {
        const input = event.target as HTMLInputElement
        if (input.files && input.files.length > 0) {
            this.selectedFile = input.files[0]
            const reader = new FileReader()
            reader.onload = () => {
                this.imagePreview = reader.result as string
            }
            reader.readAsDataURL(this.selectedFile)
            this.photoId = uuidv4()
        }
    }

    private parseLocationInput(location: string) {
        if (location === '') return null

        let positions = location.split(' ')
        return {
            latitude: positions[0],
            longitude: positions[1],
            place: '',
        }
    }
}
