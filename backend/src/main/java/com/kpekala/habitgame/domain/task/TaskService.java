package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void addTask(AddTaskRequest request);
    List<TaskDto> getUserTasks(String userEmail);
}
