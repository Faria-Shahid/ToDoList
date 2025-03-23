package com.example.todolist.tasks;

import com.example.todolist.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEndDateEqualsAndStatus(LocalDate endDate, TaskStatus status);



    public List<Task> findTaskByStatusAndUser(TaskStatus taskStatus, User user);

    List<Task> findByEndDate(LocalDate tomorrow);
}
