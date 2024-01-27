package com.kpekala.habitgame.domain.habit.dto;

import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HabitDto {
    private String name;
    private String description;
    private boolean isGood;
    private HabitDifficulty difficulty;
    private int id;
}
