package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addTask(AddTaskRequest request) {
        var newTask = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .difficulty(mapDifficulty(request.getDifficulty()))
                .build();

        var user = userRepository.findByEmailAddress(request.getEmail()).orElseThrow(UserNotFoundException::new);

        user.addTask(newTask);
        newTask.setUser(user);

        userRepository.save(user);
    }

    private Task.Difficulty mapDifficulty(AddTaskRequest.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> Task.Difficulty.EASY;
            case MEDIUM -> Task.Difficulty.MEDIUM;
            case HARD -> Task.Difficulty.HARD;
        };
    }
}
