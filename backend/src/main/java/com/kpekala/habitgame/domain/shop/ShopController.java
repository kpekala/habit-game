package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.shop.dto.BuyItemRequest;
import com.kpekala.habitgame.domain.shop.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("items")
    public List<ItemDto> getItems() {
        return shopService.getAllItems();
    }

    @PostMapping("buy")
    public void buy(@RequestBody BuyItemRequest request) {
        shopService.buyItem(request.getItemId(), request.getUserId());
    }
}
