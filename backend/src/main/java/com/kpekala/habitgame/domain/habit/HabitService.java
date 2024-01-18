package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;

public interface HabitService {

    void doHabit(int habitId);

    void removeHabit(int habitId);

    void addHabit(AddHabitRequest request);
}
