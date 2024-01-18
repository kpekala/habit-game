package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService{

    private final HabitRepository habitRepository;

    private final UserRepository userRepository;

    @Override
    public void doHabit(int habitId) {

    }

    @Override
    public void removeHabit(int habitId) {

    }

    @Override
    @Transactional
    public void addHabit(AddHabitRequest request) {
        var habit = Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .habitDifficulty(Enum.valueOf(HabitDifficulty.class, request.getDifficulty()))
                .isGood(request.isGood())
                .build();

        var user = userRepository.findByEmailAddress(request.getEmail()).orElseThrow(UserNotFoundException::new);

        habit.setUser(user);

        habitRepository.save(habit);
    }
}
