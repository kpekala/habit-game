package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.shop.dto.ItemDto;
import com.kpekala.habitgame.domain.shop.exception.NotEnoughGoldException;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{

    private final ItemRepository itemRepository;
    private final PlayerService playerService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void buyItem(int itemId, String email) {
        var item = itemRepository.findById(itemId).orElseThrow();
        var user = userRepository.findByEmailAddress(email).orElseThrow(UserNotFoundException::new);

        if (!playerService.userHasEnoughMoney(user.getId(), item.getCost())) {
            throw new NotEnoughGoldException();
        }
        playerService.loseGold(user.getId(), item.getCost());

        var player = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new).getPlayer();
        player.addItem(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(this::mapToItemDto).toList();
    }

    private ItemDto mapToItemDto(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .cost(item.getCost())
                .id(item.getId())
                .build();
    }
}
