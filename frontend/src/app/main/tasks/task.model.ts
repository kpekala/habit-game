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
    // private String title;
    // private String description;
    // private Difficulty difficulty;
    // private Date deadline;
    // private String email;

    title: string;
    description: string;
    difficulty: string;
    deadline: Date;
    email: string;
}