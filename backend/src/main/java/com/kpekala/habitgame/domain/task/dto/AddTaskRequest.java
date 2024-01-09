package com.kpekala.habitgame.domain.task.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddTaskRequest {
    private String title;
    private String description;
    private Difficulty difficulty;
    private Date deadline;
    private String email;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}