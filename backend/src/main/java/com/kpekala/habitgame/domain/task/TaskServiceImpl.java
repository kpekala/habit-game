package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.player.PlayerRepository;
import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.Difficulty;
import com.kpekala.habitgame.domain.task.dto.TaskDto;
import com.kpekala.habitgame.domain.user.UserRepository;
import com.kpekala.habitgame.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kpekala.habitgame.domain.task.dto.Difficulty.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

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

        newTask.setUser(user);

        userRepository.save(user);
        taskRepository.save(newTask);
    }

    @Override
    @Transactional
    public List<TaskDto> getUserTasks(String userEmail) {
        var user = userRepository.findByEmailAddress(userEmail).orElseThrow(UserNotFoundException::new);

        return user.getTasks().stream().map(this::mapTask).toList();
    }

    @Override
    @Transactional
    public void finishTask(int id) {
        var task = taskRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        var player = task.getUser().getPlayer();
        player.addGold(task.getGold());
        player.addExp(task.getExperience());

        taskRepository.deleteById(id);
        playerRepository.save(player);
    }

    private TaskDto mapTask(Task task) {
        return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .difficulty(mapDifficulty(task.getDifficulty()))
                .deadline(task.getDeadline())
                .id(task.getId())
                .build();
    }

    private Task.Difficulty mapDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> Task.Difficulty.EASY;
            case MEDIUM -> Task.Difficulty.MEDIUM;
            case HARD -> Task.Difficulty.HARD;
        };
    }

    private Difficulty mapDifficulty(Task.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> EASY;
            case MEDIUM -> MEDIUM;
            case HARD -> HARD;
        };
    }
}
