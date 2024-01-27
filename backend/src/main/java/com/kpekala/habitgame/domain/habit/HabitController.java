package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.habit.dto.HabitDto;
import com.kpekala.habitgame.domain.task.dto.TasksResponse;
import com.kpekala.habitgame.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @PostMapping("do")
    public void doHabit(int habitId){
        habitService.doHabit(habitId);
    }

    @DeleteMapping("delete")
    public void removeHabit(int habitId){
        habitService.removeHabit(habitId);
    }

    @PostMapping("add")
    public void addHabit(AddHabitRequest request){
        habitService.addHabit(request);
    }

    @GetMapping
    public List<HabitDto> getTasks(@RequestParam String email) {
        return habitService.getHabits(email);
    }
}
