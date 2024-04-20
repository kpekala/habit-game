package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import com.kpekala.habitgame.domain.player.dto.ItemDto;

import java.util.List;

public interface PlayerService {
    float getMaxExperience(int lvl);
    boolean loseHp(String email, float hp);
    float getDamage(HabitDifficulty habitDifficulty);
    boolean userHasEnoughMoney(long userId, float expectedMoney);
    void loseGold(long userId, float money);
    List<ItemDto> getPlayerItems(long playerId);
}
