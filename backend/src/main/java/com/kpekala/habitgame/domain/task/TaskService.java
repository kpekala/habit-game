package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.FinishTaskResponse;
import com.kpekala.habitgame.domain.task.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void addTask(AddTaskRequest request);
    List<TaskDto> getUserTasks(String userEmail);
    FinishTaskResponse finishTask(int id);

    float getExperience(Task.Difficulty difficulty);
    float getGold(Task.Difficulty difficulty);
}
