package com.kpekala.habitgame.domain.auth;

import com.kpekala.habitgame.domain.auth.dto.SignupRequest;
import com.kpekala.habitgame.domain.auth.exception.UserExistsException;
import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.RoleRepository;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public void authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void createUser(SignupRequest signupRequest) {
        boolean userExists = userRepository.existsByEmailAddress(signupRequest.getEmailAddress());
        if (userExists)
            throw new UserExistsException("User already exists in database");

        var user = prepareUser(signupRequest);
        userRepository.save(user);

        authenticateUser(signupRequest.getEmailAddress(), signupRequest.getPassword());
    }

    private User prepareUser(SignupRequest signupRequest) {
        var user = new User();
        var role = roleRepository.findByName("user").get();
        user.setRoles(Set.of(role));
        user.setPlayer(new Player());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getName());
        user.setEmailAddress(signupRequest.getEmailAddress());
        return user;
    }
}
