package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.SignupRequest;
import com.kpekala.habitgame.domain.auth.exception.UserExistsException;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testCreateUser_whenUserExists_throwException() {
        // Assume
        SignupRequest signupRequest = new SignupRequest("Tester", "test@test.pl", "123456");

        // Act and assert
        assertThrows(UserExistsException.class, () -> authService.signup(signupRequest));
    }
}
