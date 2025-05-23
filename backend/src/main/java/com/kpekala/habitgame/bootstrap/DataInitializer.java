package com.kpekala.habitgame.bootstrap;

import com.kpekala.habitgame.domain.habit.Habit;
import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import com.kpekala.habitgame.domain.habit.HabitRepository;
import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.role.Role;
import com.kpekala.habitgame.domain.role.RoleRepository;
import com.kpekala.habitgame.domain.shop.Item;
import com.kpekala.habitgame.domain.shop.ItemRepository;
import com.kpekala.habitgame.domain.task.Geolocation;
import com.kpekala.habitgame.domain.task.Task;
import com.kpekala.habitgame.domain.task.TaskRepository;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TaskRepository taskRepository;

    private final HabitRepository habitRepository;

    private final ItemRepository itemRepository;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void run(String... args) {
        if(isDatabaseInitialized()) {
            log.info("Database is already initialized");
            return;
        }
        setUpRoles();
        setUpUsers();
        setUpTasks();
        setUpHabits();
        setUpItems();
    }

    private boolean isDatabaseInitialized() {
        return roleRepository.findByName("user").isPresent();
    }

    private void setUpRoles() {
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        roleRepository.saveAll(List.of(userRole, adminRole));
    }

    private void setUpUsers() {
        User user1 = new User("Konrad", "test@test.pl", "$2a$10$3bGUcRVIwPV5IQj.35J2Fehzcm9En4f94Pc03JKOuQbMpXzTfhbNq");

        Player player1 = new Player(100);
        player1.setGold(200f);

        user1.setPlayer(player1);
        user1.setRoles(Set.of(roleRepository.findByName("admin").get()));
        user1.setCreationDate(LocalDateTime.now());

        userRepository.save(user1);
    }

    private void setUpTasks() {
        var sampleTasks = List.of(Task.builder()
                        .title("First task!")
                        .description("Lorem ipsum")
                        .difficulty(Task.Difficulty.MEDIUM)
                        .location(new Geolocation(null, 52.237049f, 21.017532f, "Warsaw"))
                        .deadline(new Date()).build(),
                Task.builder()
                        .title("Do 20 push-ups")
                        .description("Just do it")
                        .difficulty(Task.Difficulty.HARD)
                        .location(new Geolocation(null, 52.237049f, 21.017532f, "Warsaw"))
                        .deadline(new Date()).build(),
                Task.builder()
                        .title("Do 50 push-ups")
                        .description("its going to be hard asf")
                        .difficulty(Task.Difficulty.HARD)
                        .location(new Geolocation(null, 52.237049f, 21.017532f, "Warsaw"))
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

    private void setUpHabits() {
        var sampleHabits = List.of(
                Habit.builder()
                        .name("Drink coffee")
                        .description("Lorem ipsum")
                        .habitDifficulty(HabitDifficulty.EASY)
                        .isGood(true).build(),
                Habit.builder()
                        .name("Drink beer")
                        .description("Lorem ipsum")
                        .habitDifficulty(HabitDifficulty.EASY)
                        .isGood(false).build()
        );


        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                var user = userRepository.findByEmailAddress("test@test.pl").orElseThrow(UserNotFoundException::new);

                sampleHabits.forEach(habit -> {
                    user.addHabit(habit);
                    habit.setUser(user);
                    habitRepository.save(habit);
                });
            }
        });
    }

    private void setUpItems() {
        var items = List.of(
                Item.builder().name("Small health potion")
                        .description("This tiny bottle gives your body energy to conquer the world!")
                        .cost(20f)
                        .healthIncrease(10f).build(),
                Item.builder().name("Medium health potion")
                        .description("This bottle gives you extra energy and everything you need to do your goddamn job")
                        .healthIncrease(20f).cost(40f).build(),
                Item.builder().name("Big health potion")
                        .description("This bottle gives you super power!")
                        .healthIncrease(30f).cost(60f).build()
        );
        itemRepository.saveAll(items);
    }
}
