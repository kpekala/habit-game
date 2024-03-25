package com.kpekala.habitgame.domain.shop;

import com.kpekala.habitgame.domain.common.RestErrorResponse;
import com.kpekala.habitgame.domain.shop.dto.BuyItemRequest;
import com.kpekala.habitgame.domain.shop.dto.ItemDto;
import com.kpekala.habitgame.domain.shop.exception.NotEnoughGoldException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        shopService.buyItem(request.getItemId(), request.getEmail());
    }

    @ExceptionHandler({NotEnoughGoldException.class})
    public ResponseEntity<RestErrorResponse> handleException(NotEnoughGoldException exception) {
        var response = new RestErrorResponse(
                HttpStatus.FORBIDDEN.value(), exception.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
