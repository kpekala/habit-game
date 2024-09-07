package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.FinishTaskRequest;
import com.kpekala.habitgame.domain.task.dto.FinishTaskResponse;
import com.kpekala.habitgame.domain.task.dto.TasksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public TasksResponse getTasks(@RequestParam String email) {
        var tasks = taskService.getUserTasks(email);
        var tasksResponse = new TasksResponse();
        tasksResponse.setTasks(tasks);
        return tasksResponse;
    }

    @PostMapping("add")
    public void addTask(@RequestBody AddTaskRequest request) {
        taskService.addTask(request);
    }

    @PostMapping("finish")
    public FinishTaskResponse finishTask(@RequestBody FinishTaskRequest request) {
        return taskService.finishTask(request.getTaskId());
    }

    @GetMapping("completed")
    public TasksResponse getCompletedTasks(@RequestParam String email) {
        var tasks = taskService.getCompletedTasks(email);
        var tasksResponse = new TasksResponse();
        tasksResponse.setTasks(tasks);
        return tasksResponse;
    }
}
