package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.exception.UserExistsException;
import com.kpekala.habitgame.domain.role.Role;
import com.kpekala.habitgame.domain.role.RoleRepository;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    public void setup() {
        authService = new AuthServiceImpl(userRepository, roleRepository, passwordEncoder, authenticationManager);
    }

    @Test
    public void testCreateUser_whenUserDoesNotExist_createsUser() {
        SignupDto signupDto = new SignupDto("Tester", "test@test.pl", "123456");
        when(userRepository.existsByEmailAddress(anyString())).thenReturn(false);
        when(roleRepository.findByName("user")).thenReturn(Optional.of(new Role("user")));
        when(passwordEncoder.encode(anyString())).thenReturn("123456");
        when(authenticationManager.authenticate(any())).thenReturn(null);

        authService.createUser(signupDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testCreateUser_whenUserExists_throwException() {
        SignupDto signupDto = new SignupDto("Tester", "test@test.pl", "123456");
        when(userRepository.existsByEmailAddress(anyString())).thenReturn(true);

        assertThrows(UserExistsException.class, () -> authService.createUser(signupDto));
    }
}
