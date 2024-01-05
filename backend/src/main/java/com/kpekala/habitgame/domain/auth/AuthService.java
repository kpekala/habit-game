package com.kpekala.habitgame.domain.auth;

public interface AuthService {
    void authenticateUser(String email, String password);

    void createUser(SignupDto signupDto);
}
