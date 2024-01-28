package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.DoHabitResponse;
import com.kpekala.habitgame.domain.habit.dto.HabitDto;

import java.util.List;

public interface HabitService {

    DoHabitResponse doHabit(int habitId);

    void removeHabit(int habitId);

    void addHabit(AddHabitRequest request);

    List<HabitDto> getHabits(String userEmail);

    float getGold(Habit habit);

}
