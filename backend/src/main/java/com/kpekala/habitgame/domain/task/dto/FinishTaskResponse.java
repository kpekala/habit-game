package com.kpekala.habitgame.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinishTaskResponse {
    private boolean leveledUp;
    private int currentLevel;
}
