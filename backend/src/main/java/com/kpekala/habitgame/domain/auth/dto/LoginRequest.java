package com.kpekala.habitgame.domain.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
