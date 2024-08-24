package com.kpekala.habitgame.domain.habit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddHabitRequest {
    private String title;
    private String description;
    private boolean isGood;
    private String difficulty;
    private String email;
}
