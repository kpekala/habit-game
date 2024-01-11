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