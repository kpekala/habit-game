package com.kpekala.habitgame.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String emailAddress;
    private String password;
}
