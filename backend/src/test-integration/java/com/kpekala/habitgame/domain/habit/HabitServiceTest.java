package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.common.ExperienceAdder;
import com.kpekala.habitgame.domain.habit.dto.DoHabitRequest;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HabitServiceTest {

    @Autowired
    private HabitService habitService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private ExperienceAdder experienceAdder;

    @Test
    @Transactional
    @DirtiesContext
    public void testRemoveHabit_whenHabitIsInDatabase_removeIt() {
        // Assume
        var user = userRepository.findByEmailAddress("test@test.pl").orElseThrow();
        assertThat(user.getHabits()).hasSize(2);
        var habitId = user.getHabits().get(0).getId();

        // Act
        habitService.removeHabit(habitId);

        // Assert
        user = userRepository.findByEmailAddress("test@test.pl").orElseThrow();
        assertThat(user.getHabits()).hasSize(1);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testDoPositiveHabit() {
        // Assume
        var user = userRepository.findByEmailAddress("test@test.pl").get();
        var habit = Habit.builder()
                .name("Title")
                .description("Description")
                .habitDifficulty(HabitDifficulty.MEDIUM)
                .isGood(true)
                .user(user)
                .build();
        habitRepository.save(habit);

        // Act
        habitService.doHabit(new DoHabitRequest(habit.getId()));

        // Assert
        var player = userRepository.findByEmailAddress("test@test.pl").get().getPlayer();
        assertEquals(experienceAdder.getExperience(habit.getHabitDifficulty(), true), player.getExperience());
        assertEquals(habitService.getGold(habit), player.getGold());
    }
}
