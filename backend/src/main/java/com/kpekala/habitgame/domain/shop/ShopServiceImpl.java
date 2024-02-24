package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.shop.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{

    private final ItemRepository itemRepository;

    @Override
    public void buyItem(int itemId, int userId) {

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
                .build();
    }
}
