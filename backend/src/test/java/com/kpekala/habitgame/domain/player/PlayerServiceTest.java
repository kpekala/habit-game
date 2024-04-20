package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.player.dto.ItemDto;
import com.kpekala.habitgame.domain.shop.Item;
import com.kpekala.habitgame.domain.user.User;
import com.kpekala.habitgame.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testGetPLayerItems() {
        var playerId = 1234L;
        var user = new User();
        user.setId(playerId);
        var player = new Player();
        var items = List.of(
                new Item(1, 10f, "Small potion", "Small potion", null),
                new Item(1, 10f, "Small potion", "Small potion", null),
                new Item(2, 20f, "Potion", "Regular potion", null)
        );
        player.setItems(items);
        user.setPlayer(player);

        when(userRepository.findById(playerId)).thenReturn(Optional.of(user));

        var playerItems = playerService.getPlayerItems(playerId);

        assertThat(playerItems).hasSize(2);
        assertThat(playerItems).containsExactlyInAnyOrder(
                new ItemDto("Potion", "Regular potion", 1, 2),
                new ItemDto("Small potion", "Small potion", 2, 1)
        );
    }
}
