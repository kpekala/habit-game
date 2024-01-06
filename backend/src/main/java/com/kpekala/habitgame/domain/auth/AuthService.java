package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.SignupRequest;

public interface AuthService {
    void authenticateUser(String email, String password);

    void createUser(SignupRequest signupRequest);
}
