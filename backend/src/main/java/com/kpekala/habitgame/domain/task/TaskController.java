package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.TasksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    TasksResponse getTasks(@RequestParam String email) {
        var tasks = taskService.getUserTasks(email);
        var tasksResponse = new TasksResponse();
        tasksResponse.setTasks(tasks);
        return tasksResponse;
    }
}
