package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.shop.dto.ItemDto;

import java.util.List;

public interface ShopService {

    void buyItem(int itemId, long userId);

    List<ItemDto> getAllItems();
}
