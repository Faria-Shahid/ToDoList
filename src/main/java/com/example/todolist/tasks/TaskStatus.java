package com.example.todolist.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
   IN_PROGRESS, COMPLETED, OVERDUE;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("TaskStatus value cannot be null or empty");
        }
        value = value.trim().toUpperCase(); // Remove spaces and convert to uppercase
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
    }

    @JsonValue
    public String toValue() {
        return name().toUpperCase();
    }
}
