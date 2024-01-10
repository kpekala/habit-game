export interface Task {
    title: string;
    description: string;
    difficulty: string;
    deadline: Date;
}

export interface TasksResponse {
    tasks: Task[]
}