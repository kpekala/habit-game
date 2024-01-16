package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.LoginRequest;
import com.kpekala.habitgame.domain.auth.dto.SignupRequest;
import com.kpekala.habitgame.domain.auth.exception.UserExistsException;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Test
    @DirtiesContext
    public void testSignUp_whenUserDoesNotExist_createsUser() {
        // Assume
        var request = new SignupRequest("Tester1", "test1@test.pl", "123456");

        // Act
        var response = authService.signup(request);

        // Assert
        var userOptional = userRepository.findByEmailAddress("test1@test.pl");
        assertTrue(userOptional.isPresent());
        assertTrue(jwtService.isTokenValid(response.getToken(), userOptional.get()));
    }

    @Test
    public void testSignUp_whenUserExists_throwException() {
        // Assume
        var request = new SignupRequest("Tester", "test@test.pl", "123456");

        // Act and assert
        assertThrows(UserExistsException.class, () -> authService.signup(request));
    }

    @Test
    public void testSignIn_whenUserExists_signUserIn() {
        // Assume
        var request = new LoginRequest("test@test.pl", "123456");

        // Act
        var response = authService.signin(request.getEmailAddress(), request.getPassword());

        // Assert
        var user = userRepository.findByEmailAddress("test@test.pl").get();
        assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        assertTrue(jwtService.isTokenValid(response.getToken(), user));
    }

    @Test
    public void testSignIn_whenUserDoesNotExist_throwException() {
        // Assume
        var request = new LoginRequest("test1@test.pl", "123456");

        assertThrows(BadCredentialsException.class, () ->
                authService.signin(request.getEmailAddress(), request.getPassword()));
    }
}
