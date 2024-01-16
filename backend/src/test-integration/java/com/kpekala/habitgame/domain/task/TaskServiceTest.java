package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.player.PlayerRepository;
import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.Difficulty;
import com.kpekala.habitgame.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DirtiesContext
    @Transactional
    public void testAddTask() {
        // Assume
        var request = new AddTaskRequest("Test title", "Test description", Difficulty.EASY,
                Date.from(Instant.now()), "test@test.pl");

        var user = userRepository.findByEmailAddress("test@test.pl").get();
        var tasksSizeBefore = user.getTasks().size();

        // Act
        taskService.addTask(request);

        // Assert
        user = userRepository.findByEmailAddress("test@test.pl").get();
        assertThat(user.getTasks()).hasSize(tasksSizeBefore + 1);
    }

    @Test
    @DirtiesContext
    @Transactional
    public void testFinishTask() {
        // Assume
        var user = userRepository.findByEmailAddress("test@test.pl").get();
        var task = Task.builder()
                .title("Title")
                .description("Description")
                .difficulty(Task.Difficulty.EASY)
                .user(user)
                .build();
        taskRepository.save(task);

        // Act
        taskService.finishTask(task.getId());

        // Assert
        var player = userRepository.findByEmailAddress("test@test.pl").get().getPlayer();
        assertEquals(taskService.getExperience(Task.Difficulty.EASY), player.getExperience());
        assertEquals(taskService.getGold(Task.Difficulty.EASY), player.getGold());

    }
}
