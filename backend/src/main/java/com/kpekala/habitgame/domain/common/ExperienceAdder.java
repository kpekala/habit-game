package com.kpekala.habitgame.domain.common;

import com.kpekala.habitgame.domain.habit.Habit;
import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceAdder {

    private final PlayerService playerService;

    public boolean addExperienceToPlayer(Habit habit, Player player) {
        float newExp = player.getExperience() + getExperience(habit.getHabitDifficulty(), habit.isGood());
        return addExperienceToPlayer(player, newExp);
    }

    public boolean addExperienceToPlayer(Task task, Player player) {
        float newExp = player.getExperience() + getExperience(task.getDifficulty());
        return addExperienceToPlayer(player, newExp);
    }

    private boolean addExperienceToPlayer(Player player, float newExperience) {
        float maxExp = playerService.getMaxExperience(player.getLvl());

        boolean leveledUp = false;
        if (newExperience >= maxExp) {
            newExperience -= maxExp;
            player.setLvl(player.getLvl() + 1);
            leveledUp = true;
        }
        player.setExperience(newExperience);

        return leveledUp;
    }

    public float getExperience(HabitDifficulty difficulty, boolean isGood) {
        if (!isGood)
            return 0f;
        return switch (difficulty){
            case EASY -> 5f;
            case MEDIUM -> 10f;
            case HARD -> 20f;
        };
    }

    public float getExperience(Task.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 5f;
            case MEDIUM -> 10f;
            case HARD -> 20f;
        };
    }
}


