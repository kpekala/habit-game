package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.player.dto.ItemDto;
import com.kpekala.habitgame.domain.player.dto.UseItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("items")
    public List<ItemDto> getPlayerItems(@RequestParam String email) {
        return playerService.getPlayerItems(email);
    }

    @PostMapping("items/use")
    public void useItem(@RequestBody UseItemRequest useItemRequest) {
        playerService.useItem(useItemRequest.getEmail(), useItemRequest.getId());
    }
}
