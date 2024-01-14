package com.kpekala.habitgame.domain.task.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AddTaskRequest {
    private String title;
    private String description;
    private Difficulty difficulty;
    private Date deadline;
    private String email;
}