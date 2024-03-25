export interface ItemDto {
    name: string;
    description: string;
    cost: number;
    id: number;
}

export interface BuyItemRequest {
    itemId: number;
    email: string;
}