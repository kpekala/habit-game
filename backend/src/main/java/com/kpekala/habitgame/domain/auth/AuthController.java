package com.kpekala.habitgame.domain.auth;

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
    public String authenticateUser(@RequestBody LoginDto loginDto){
        authService.authenticateUser(loginDto.getEmailAddress(), loginDto.getPassword());

        return "User signed-in successfully!";
    }

    @PostMapping("signup")
    public String signUp(@RequestBody SignupDto signupDto) {
        authService.createUser(signupDto);

        return "User created successfully!";
    }
}
