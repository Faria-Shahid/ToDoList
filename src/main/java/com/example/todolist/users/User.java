package com.example.todolist.users;

import com.example.todolist.tasks.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Task> tasks;

    public User(Long userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;  // Initialize email
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;  // Getter for email
    }

    public void setEmail(String email) {
        this.email = email;  // Setter for email
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
