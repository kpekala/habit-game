package com.kpekala.habitgame.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {
    private String name;
    private String description;
    private float cost;
}
