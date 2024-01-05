package com.kpekala.habitgame.bootstrap;

import com.kpekala.habitgame.player.Player;
import com.kpekala.habitgame.user.UserDetails;
import com.kpekala.habitgame.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        setUpUsers();
    }

    private void setUpUsers() {
        UserDetails user1 = new UserDetails("Konrad", "test@test.pl", "123456");

        Player player1 = new Player(100);

        user1.setPlayer(player1);
        player1.setUser(user1);

        userRepository.save(user1);
    }
}
