package com.example.todolist.tasks;

import com.example.todolist.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    private User user;

    @Size(max = 30)
    @NotNull
    private String taskTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

//    @Future(message = "End date should be in the future.")
    @NotNull
    private LocalDate endDate;

    public Task() {}

    public Task(long taskId, String taskTitle, LocalDate endDate,TaskStatus status) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.endDate = endDate;
        this.status = status;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @PrePersist
    @PreUpdate
    public void validateEndDate() {
        if (status != TaskStatus.OVERDUE && endDate != null && endDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date must be in the future.");
        }
    }

}
