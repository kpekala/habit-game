package com.kpekala.habitgame;

import com.kpekala.habitgame.player.Player;
import com.kpekala.habitgame.user.UserDetails;
import com.kpekala.habitgame.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HabitgameApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HabitgameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        play();
    }

    @Transactional
    public void play() {
        UserDetails user1 = new UserDetails("Konrad", "test@test.pl", "123456");

        Player player1 = new Player(100);

        user1.setPlayer(player1);
        player1.setUser(user1);

        userRepository.save(user1);
    }
}
