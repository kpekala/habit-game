package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.common.ExperienceAdder;
import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.DoHabitRequest;
import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.player.PlayerRepository;
import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    HabitRepository habitRepository;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    ExperienceAdder experienceAdder;

    @Mock
    PlayerService playerService;

    @Captor
    ArgumentCaptor<Habit> habitCaptor;

    @InjectMocks
    HabitServiceImpl habitService;

    @Test
    public void testAddHabit() {
        // Assume
        var user = new User("tester", "test@test.pl", "123456");
        when(userRepository.findByEmailAddress("test@test.pl")).thenReturn(Optional.of(user));
        var request = new AddHabitRequest("name", "description", true,
                "HARD", "test@test.pl");

        // Act
        habitService.addHabit(request);

        // Assert
        var expectedHabit = new Habit(null, "name", "description",
                true, HabitDifficulty.HARD, user);
        verify(habitRepository, times(1)).save(habitCaptor.capture());

        var habit = habitCaptor.getValue();
        assertEquals(expectedHabit.getName(), habit.getName());
        assertEquals(expectedHabit.getDescription(), habit.getDescription());
        assertEquals(expectedHabit.getHabitDifficulty(), habit.getHabitDifficulty());
    }

    @Test
    public void testGetHabit() {
        // Assume
        var habitEntity = new Habit(1, "Test title", "Test description", true, HabitDifficulty.MEDIUM, null);
        var user = new User("tester", "test@test.pl", "123456");
        user.setHabits(List.of(habitEntity));

        when(userRepository.findByEmailAddress("test@test.pl")).thenReturn(Optional.of(user));

        // Act
        var habitDtos = habitService.getHabits("test@test.pl");

        // Assert
        assertThat(habitDtos).hasSize(1);
        var habitDto = habitDtos.get(0);
        assertEquals(habitEntity.getName(), habitDto.getName());
        assertEquals(habitEntity.getDescription(), habitDto.getDescription());
        assertEquals(habitEntity.getHabitDifficulty(), habitDto.getDifficulty());
        assertEquals(habitEntity.getId(), habitDto.getId());
        assertEquals(habitEntity.isGood(), habitDto.isGood());
    }

    @Test
    public void testDoHabit_doPositiveHabit() {
        // Assume
        var user = new User();
        var player = new Player();
        user.setPlayer(player);
        player.setLvl(1);
        var habitEntity = new Habit(1, "Test title", "Test description", true, HabitDifficulty.MEDIUM, user);

        when(habitRepository.findById(1)).thenReturn(Optional.of(habitEntity));
        when(experienceAdder.addExperienceToPlayer(any(Habit.class), any())).thenReturn(false);

        // Act
        var request = new DoHabitRequest(1);
        var response = habitService.doHabit(request);

        // Assert
        assertFalse(response.isLeveledUp());
        assertFalse(response.isDead());
        assertEquals(1, response.getCurrentLevel());
    }
}
