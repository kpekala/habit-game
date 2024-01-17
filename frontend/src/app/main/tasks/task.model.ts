export interface Task {
    title: string;
    description: string;
    difficulty: string;
    deadline: Date;
    id: string;
}

export interface TasksResponse {
    tasks: Task[]
}

export interface AddTaskRequest {
    title: string;
    description: string;
    difficulty: string;
    deadline: Date;
    email: string;
}

export interface FinishTaskResponse {
    leveledUp: boolean;
    currentLevel: number;
}