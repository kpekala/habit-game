package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.habit.HabitDifficulty;

public interface PlayerService {
    float getMaxExperience(int lvl);
    boolean loseHp(String email, float hp);
    float getDamage(HabitDifficulty habitDifficulty);
}
