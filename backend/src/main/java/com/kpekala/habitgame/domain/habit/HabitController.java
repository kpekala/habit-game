package com.kpekala.habitgame.domain.habit;

import com.kpekala.habitgame.domain.habit.dto.AddHabitRequest;
import com.kpekala.habitgame.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final UserService userService;

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
}
