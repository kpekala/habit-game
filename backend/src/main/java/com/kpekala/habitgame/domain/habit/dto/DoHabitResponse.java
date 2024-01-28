package com.kpekala.habitgame.domain.habit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoHabitResponse {
    private boolean leveledUp;
    private int currentLevel;
}