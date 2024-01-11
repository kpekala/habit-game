package com.kpekala.habitgame.bootstrap;

import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.Role;
import com.kpekala.habitgame.domain.role.RoleRepository;
import com.kpekala.habitgame.domain.task.Task;
import com.kpekala.habitgame.domain.task.TaskRepository;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TaskRepository taskRepository;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void run(String... args) {
        setUpUsers();
        setUpRoles();
        setUpTasks();
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

    private void setUpTasks() {
        var sampleTasks = List.of(Task.builder()
                        .title("First task!")
                        .description("Lorem ipsum")
                        .difficulty(Task.Difficulty.EASY)
                        .deadline(new Date()).build(),
                Task.builder()
                        .title("Do 20 push-ups")
                        .description("Just do it")
                        .difficulty(Task.Difficulty.MEDIUM)
                        .deadline(new Date()).build(),
                Task.builder()
                        .title("Do 50 push-ups")
                        .description("its going to be hard asf")
                        .difficulty(Task.Difficulty.MEDIUM)
                        .deadline(new Date()).build());


        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                var user = userRepository.findByEmailAddress("test@test.pl").orElseThrow(UserNotFoundException::new);

                sampleTasks.forEach(task -> {
                    user.addTask(task);
                    task.setUser(user);
                    taskRepository.save(task);
                });
            }
        });
    }
}
