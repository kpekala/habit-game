package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.player.Player;
import com.kpekala.habitgame.domain.player.PlayerRepository;
import com.kpekala.habitgame.domain.player.PlayerService;
import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.Difficulty;
import com.kpekala.habitgame.domain.task.dto.FinishTaskResponse;
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
    private final PlayerRepository playerRepository;

    private final PlayerService playerService;

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
        user.addTask(newTask);

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
    public FinishTaskResponse finishTask(int id) {
        var task = taskRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        var player = task.getUser().getPlayer();
        player.addGold(getGold(task.getDifficulty()));

        boolean leveledUp = addExperienceToPlayer(task, player);

        taskRepository.deleteById(id);
        playerRepository.save(player);

        return new FinishTaskResponse(leveledUp, player.getLvl());
    }

    private boolean addExperienceToPlayer(Task task, Player player) {
        float newExp = player.getExperience() + getExperience(task.getDifficulty());
        float maxExp = playerService.getMaxExperience(player.getLvl());

        boolean leveledUp = false;
        if (newExp >= maxExp) {
            newExp -= maxExp;
            player.setLvl(player.getLvl() + 1);
            leveledUp = true;
        }
        player.setExperience(newExp);

        return leveledUp;
    }

    public float getExperience(Task.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 5f;
            case MEDIUM -> 10f;
            case HARD -> 20f;
        };
    }

    public float getGold(Task.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 10f;
            case MEDIUM -> 25f;
            case HARD -> 50f;
        };
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
