package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.common.ExperienceAdder;
import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.DoHabitResponse;
import com.kpekala.habitgame.domain.habit.dto.HabitDto;
import com.kpekala.habitgame.domain.player.PlayerRepository;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HabitServiceImpl implements HabitService{

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    private final ExperienceAdder experienceAdder;

    @Override
    @Transactional
    public DoHabitResponse doHabit(int habitId) {
        var habit = habitRepository.findById(habitId).orElseThrow();
        var user = habit.getUser();
        var player = user.getPlayer();
        float goldGain = getGold(habit);

        boolean isNewLvl = experienceAdder.addExperienceToPlayer(habit, player);
        player.addGold(goldGain);

        playerRepository.save(player);

        return new DoHabitResponse(isNewLvl, player.getLvl());
    }

    @Override
    @Transactional
    public void removeHabit(int habitId) {
        var habit = habitRepository.findById(habitId).orElseThrow();
        var user = habit.getUser();
        user.removeHabit(habit);
        userRepository.save(user);
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

    @Override
    @Transactional
    public List<HabitDto> getHabits(String userEmail) {
        var user = userRepository.findByEmailAddress(userEmail).orElseThrow(UserNotFoundException::new);

        return mapToHabitDtos(user.getHabits());
    }

    public float getGold(Habit habit) {
        if (!habit.isGood())
            return 0f;
        return switch (habit.getHabitDifficulty()){
            case EASY -> 10f;
            case MEDIUM -> 25f;
            case HARD -> 50f;
        };
    }

    private List<HabitDto> mapToHabitDtos(List<Habit> habits) {
        return habits.stream().map(habit -> new HabitDto(habit.getName(), habit.getDescription(),
                habit.isGood(), habit.getHabitDifficulty(), habit.getId())).toList();
    }


}
