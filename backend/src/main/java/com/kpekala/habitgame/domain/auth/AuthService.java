package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.AuthResponse;
import com.kpekala.habitgame.domain.auth.dto.SignupRequest;

public interface AuthService {
    AuthResponse signin(String email, String password);

    AuthResponse signup(SignupRequest signupRequest);
}
