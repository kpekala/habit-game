package com.kpekala.habitgame.domain.player;

import com.kpekala.habitgame.domain.player.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("items")
    public List<ItemDto> getPlayerItems(@RequestParam Integer userId) {
        return playerService.getPlayerItems(userId);
    }
}
