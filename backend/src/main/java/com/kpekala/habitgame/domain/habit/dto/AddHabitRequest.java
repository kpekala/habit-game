package com.kpekala.habitgame.domain.habit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddHabitRequest {
    private String name;
    private String description;
    private boolean isGood;
    private String difficulty;
    private String email;
}
