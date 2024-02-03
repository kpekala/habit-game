package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final float HP_AFTER_DEATH = 20f;

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    @Override
    public float getMaxExperience(int lvl) {
        return lvl * 25;
    }

    @Override
    @Transactional
    public boolean loseHp(String email, float damage) {
        var player = userRepository.findByEmailAddress(email).orElseThrow(UserNotFoundException::new).getPlayer();
        player.setHp(player.getHp() - damage);
        playerRepository.save(player);
        if (player.getHp() <= 0){
            player.setGold(0f);
            player.setHp(HP_AFTER_DEATH);
            return true;
        }
        return false;
    }

    @Override
    public float getDamage(HabitDifficulty habitDifficulty) {
        return switch (habitDifficulty) {
            case EASY -> 15f;
            case MEDIUM -> 10f;
            case HARD -> 5f;
        };
    }
}
