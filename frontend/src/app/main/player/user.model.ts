export interface UserResponse {
    name: string;
    emailAddress: string;
    level: number;
    experience: number;
    maxExperience: number;
    gold: number;
    health: number;
    creationTime: Date;
}

export interface PlayerItemDto {
    name: string;
    description: string;
    id: number;
    count: number;
}