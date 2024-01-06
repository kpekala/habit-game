package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.LoginRequest;
import com.kpekala.habitgame.domain.auth.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public String authenticateUser(@RequestBody LoginRequest loginRequest){
        authService.authenticateUser(loginRequest.getEmailAddress(), loginRequest.getPassword());

        return "User signed-in successfully!";
    }

    @PostMapping("signup")
    public String signUp(@RequestBody SignupRequest signupRequest) {
        authService.createUser(signupRequest);

        return "User created successfully!";
    }
}
