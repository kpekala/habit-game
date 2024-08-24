package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.DoHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.DoHabitResponse;
import com.kpekala.habitgame.domain.habit.dto.HabitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("do")
    public DoHabitResponse doHabit(@RequestBody DoHabitRequest request){
        return habitService.doHabit(request);
    }

    @DeleteMapping("delete")
    public void removeHabit(int habitId){
        habitService.removeHabit(habitId);
    }

    @PostMapping("add")
    public void addHabit(@RequestBody AddHabitRequest request){
        habitService.addHabit(request);
    }

    @GetMapping
    public List<HabitDto> getTasks(@RequestParam String email) {
        return habitService.getHabits(email);
    }
}
