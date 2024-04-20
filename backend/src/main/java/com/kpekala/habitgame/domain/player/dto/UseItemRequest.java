package com.kpekala.habitgame.domain.player.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UseItemRequest {
    private String email;
    private int id;
}
