package com.kpekala.habitgame.domain.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String name;
    private String emailAddress;
    private int level;
    private float experience;
    private float maxExperience;
    private float gold;
    private LocalDateTime creationTime;
}
