package com.kpekala.habitgame.domain.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Override
    public float calculateMaxExperience(int level) {
        return 50 + level * 20;
    }
}
