package com.kpekala.habitgame.bootstrap;

import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.Role;
import com.kpekala.habitgame.domain.role.RoleRepository;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        setUpUsers();
        setUpRoles();
    }

    private void setUpRoles() {
        Role userRole = new Role("user");

        roleRepository.save(userRole);
    }

    private void setUpUsers() {
        User user1 = new User("Konrad", "test@test.pl", "$2a$10$3bGUcRVIwPV5IQj.35J2Fehzcm9En4f94Pc03JKOuQbMpXzTfhbNq");

        Role adminRole = new Role("admin");

        Player player1 = new Player(100);

        user1.setPlayer(player1);
        user1.setRoles(Set.of(adminRole));
        user1.setCreationDate(LocalDateTime.now());

        userRepository.save(user1);
    }
}
