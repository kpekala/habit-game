package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.common.ExperienceAdder;
import com.kpekala.habitgame.domain.task.dto.*;
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

    private final ExperienceAdder experienceAdder;

    @Override
    @Transactional
    public void addTask(AddTaskRequest request) {
        var newTask = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .difficulty(mapDifficulty(request.getDifficulty()))
                .location(mapToGeolocation(request.getLocation()))
                .photoId(request.getPhotoId())
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

        return user.getTasks().stream().filter(task -> !task.isCompleted()).map(this::mapTask).toList();
    }

    @Override
    @Transactional
    public List<TaskDto> getCompletedTasks(String userEmail) {
        var user = userRepository.findByEmailAddress(userEmail).orElseThrow(UserNotFoundException::new);

        return user.getTasks().stream()
                .filter(Task::isCompleted)
                .map(this::mapTask).toList();
    }

    @Override
    @Transactional
    public FinishTaskResponse finishTask(int id) {
        var task = taskRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        var player = task.getUser().getPlayer();
        player.addGold(getGold(task.getDifficulty()));

        boolean leveledUp = experienceAdder.addExperienceToPlayer(task, player);

        task.setCompleted(true);

        return new FinishTaskResponse(leveledUp, player.getLvl());
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
                .completed(task.isCompleted())
                .location(mapToLocation(task.getLocation()))
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

    private Geolocation mapToGeolocation(Location location) {
        if(location == null) return null;
        var loc = new Geolocation();
        loc.setLatitude(location.getLatitude());
        loc.setLongitude(location.getLongitude());
        loc.setPlace(location.getPlace());
        return loc;
    }

    private Location mapToLocation(Geolocation geolocation) {
        if(geolocation == null) return null;
        return new Location(geolocation.getLatitude(), geolocation.getLongitude(), geolocation.getPlace());
    }
}
