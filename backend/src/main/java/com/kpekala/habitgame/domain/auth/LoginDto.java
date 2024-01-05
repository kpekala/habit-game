package com.kpekala.habitgame.domain.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String emailAddress;
    private String password;
}
