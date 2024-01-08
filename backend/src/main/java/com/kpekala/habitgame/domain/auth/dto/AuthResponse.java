package com.kpekala.habitgame.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthResponse {

    private String token;
    private Date tokenExpirationDate;
}
