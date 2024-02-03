export interface HabitDto {
    name: string;
    description: string;
    good: boolean;
    difficulty: string;
    id: number;
}

export interface DoHabitResponse {
    leveledUp: boolean;
    currentLevel: number;
    dead: boolean;
}