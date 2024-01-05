package com.kpekala.habitgame.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupDto {

    private String name;
    private String emailAddress;
    private String password;
}
