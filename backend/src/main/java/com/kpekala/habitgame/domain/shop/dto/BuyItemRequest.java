package com.kpekala.habitgame.domain.shop.dto;

import lombok.Data;

@Data
public class BuyItemRequest {
    private int itemId;
    private long userId;
}
