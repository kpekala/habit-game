package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    HabitRepository habitRepository;

    @Captor
    ArgumentCaptor<Habit> habitCaptor;

    HabitService habitService;

    @BeforeEach
    void setup() {
        habitService = new HabitServiceImpl(habitRepository, userRepository);
    }

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
}
