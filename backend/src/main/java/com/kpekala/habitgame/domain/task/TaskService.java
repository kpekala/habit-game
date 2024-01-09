package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;

public interface TaskService {
    void addTask(AddTaskRequest request);
}
