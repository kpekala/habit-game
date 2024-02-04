package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PlayerServiceImpl playerService;

    @Test
    public void testLoseHp_whenGetsDamageBiggerThatHp_dies() {
        String email = "test@test.pl";
        var user = new User();
        var player = new Player();
        player.setGold(20f);
        player.setHp(5f);
        user.setPlayer(player);

        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(user));

        boolean isDead = playerService.loseHp(email, 20);

        assertTrue(isDead);
        assertEquals(0f, player.getGold());
        assertEquals(PlayerServiceImpl.HP_AFTER_DEATH, player.getHp());
    }
}
