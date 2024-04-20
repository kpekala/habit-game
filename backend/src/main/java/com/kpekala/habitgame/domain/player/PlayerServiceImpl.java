package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.habit.HabitDifficulty;
import com.kpekala.habitgame.domain.player.dto.ItemDto;
import com.kpekala.habitgame.domain.shop.Item;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    public static final float HP_AFTER_DEATH = 20f;

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

    @Override
    public boolean userHasEnoughMoney(long userId, float expectedMoney) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getPlayer().getGold() >= expectedMoney;
    }

    @Override
    @Transactional
    public void loseGold(long userId, float money) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var player = user.getPlayer();
        player.setGold(player.getGold() - money);
    }

    @Override
    @Transactional
    public List<ItemDto> getPlayerItems(String email) {
        var user = userRepository.findByEmailAddress(email).orElseThrow(UserNotFoundException::new);
        var player = user.getPlayer();
        var items = player.getItems();

        var itemsMap = new HashMap<Integer, ItemDto>();
        for (Item item: items) {
            if (!itemsMap.containsKey(item.getId())){
                itemsMap.put(item.getId(), new ItemDto(item.getName(), item.getDescription(), 1, item.getId()));
            }else {
                var itemDto = itemsMap.get(item.getId());
                itemDto.setCount(itemDto.getCount() + 1);
                itemsMap.put(item.getId(), itemDto);
            }
        }
        return itemsMap.values().stream().toList();
    }
}
