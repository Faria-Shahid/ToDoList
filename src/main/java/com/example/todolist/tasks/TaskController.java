package com.example.todolist.tasks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    final private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<Task>> getAllTasksOfUser(@PathVariable long userId){
        return taskService.getAllTasksOfUser(userId);
    }

    @GetMapping("/tasks/in-progress/{userId}")
    public ResponseEntity<List<Task>> getAllTasksInProgress(@PathVariable long userId){
        return taskService.getTasksInProgressOfUser(userId);
    }

    @PostMapping("/tasks/{userId}")
    public ResponseEntity<String> addTaskOfUser(@PathVariable long userId,@RequestBody Task task){
        return taskService.addTask(userId,task);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTaskOfUser(@PathVariable long taskId){
        return taskService.deleteTask(taskId);
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<String> updateTaskFields(@PathVariable long taskId, @RequestBody Map<String, Object> updates) {
        return taskService.updateTask(taskId,updates);
    }

}
