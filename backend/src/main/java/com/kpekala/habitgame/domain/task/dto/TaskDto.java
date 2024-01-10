package com.kpekala.habitgame.domain.task.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TaskDto {

    private String title;

    private String description;

    private Difficulty difficulty;

    private Date deadline;
}
