export interface Task {
    title: string
    description: string
    difficulty: string
    deadline: Date
    id: number
    location: Location
}

export interface TasksResponse {
    tasks: Task[]
}

export interface AddTaskRequest {
    title: string
    description: string
    difficulty: string
    deadline: Date
    email: string
    location: Location
}

export interface FinishTaskResponse {
    leveledUp: boolean
    currentLevel: number
}

export enum TaskType {
    TASK = 1,
    HABIT = 2,
}

export interface Location {
    longitude: number
    latitude: number
    place: string
}
