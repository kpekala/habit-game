package com.kpekala.habitgame.domain.task;

import com.kpekala.habitgame.domain.task.dto.AddTaskRequest;
import com.kpekala.habitgame.domain.task.dto.FinishTaskRequest;
import com.kpekala.habitgame.domain.task.dto.FinishTaskResponse;
import com.kpekala.habitgame.domain.task.dto.TasksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    private static final String UPLOAD_DIR = "uploads/";

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

    @PostMapping("upload-photo")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("photoId") String photoId
    ) {
        try {
            // Ensure upload dir exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Build the file path
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String filename = photoId + extension;

            Path filePath = Paths.get(UPLOAD_DIR + filename);

            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("Photo uploaded successfully with ID: " + photoId);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("completed")
    public TasksResponse getCompletedTasks(@RequestParam String email) {
        var tasks = taskService.getCompletedTasks(email);
        var tasksResponse = new TasksResponse();
        tasksResponse.setTasks(tasks);
        return tasksResponse;
    }
}
