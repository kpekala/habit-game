package com.kpekala.habitgame;

import com.kpekala.habitgame.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HabitgameApplication {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HabitgameApplication.class, args);
    }
}
