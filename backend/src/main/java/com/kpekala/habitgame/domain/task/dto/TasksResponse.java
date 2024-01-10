package com.kpekala.habitgame.domain.task.dto;

import lombok.Data;

import java.util.List;

@Data
public class TasksResponse {
    private List<TaskDto> tasks;
}
