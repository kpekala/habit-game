package com.kpekala.habitgame.domain.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public float getMaxExperience(int lvl) {
        return lvl * 25;
    }
}
