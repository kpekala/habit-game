package com.kpekala.habitgame.domain.auth;

import lombok.Data;

@Data
public class SignupDto {

    private String name;
    private String emailAddress;
    private String password;
}
