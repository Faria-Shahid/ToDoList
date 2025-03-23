package com.example.todolist.tasks;

import com.example.todolist.email.EmailService;
import com.example.todolist.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.todolist.users.User;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository,UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Task>> getAllTasksOfUser(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();

            return ResponseEntity.ok(user.getTasks());
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Task>> getTasksInProgressOfUser(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();

            return ResponseEntity.ok(taskRepository.findTaskByStatusAndUser(TaskStatus.IN_PROGRESS,user));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> addTask(long userId, Task task) {
        if (task.getEndDate().isAfter(LocalDate.now())){
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                task.setUser(user);
                task.setStatus(TaskStatus.IN_PROGRESS);
                taskRepository.save(task);
                return ResponseEntity.status(HttpStatus.CREATED).body("Task added successfully!");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date: End date must be in the future.");
    }

    public ResponseEntity<String> updateTask(long taskId, Map<String, Object> updates){
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()){
            Task task = optionalTask.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "taskTitle":
                        task.setTaskTitle((String) value);
                        break;
                    case "endDate":
                        task.setEndDate(LocalDate.parse((String) value));
                        break;
                    case "status":
                        task.setStatus(TaskStatus.fromString((value.toString())));
                        break;
                }
            });

            taskRepository.save(task);

            return ResponseEntity.ok("Task updated successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    public ResponseEntity<String> deleteTask(long taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()){
           Task task = optionalTask.get();

           taskRepository.delete(task);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    @Scheduled(cron = "0 * * * * *")
    public void markOverDueTasks(){
        LocalDate now = LocalDate.now();

        List<Task> overdueTasks = taskRepository.findByEndDateEqualsAndStatus(now, TaskStatus.IN_PROGRESS);

        overdueTasks.forEach(task -> task.setStatus(TaskStatus.OVERDUE));
        taskRepository.saveAll(overdueTasks);

        System.out.println("Checked for overdue tasks at: " + now);
        System.out.println("Found overdue tasks: " + overdueTasks.size());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendReminders() {
        // Get the current date and calculate the next day's date
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Get all tasks where due date is tomorrow
        List<Task> tasksDueTomorrow = taskRepository.findByEndDate(tomorrow);

        // Loop through the tasks and send reminders
//        for (Task task : tasksDueTomorrow) {
//            sendReminderEmail(task);
//        }
    }

    // This method will send the reminder email
//    public void sendReminderEmail(Task task) {
//        String subject = "Reminder: Your task is due tomorrow!";
//        String body = "Dear User, \n\nThis is a reminder that the following task is due tomorrow:\n"
//                + "Task: " + task.getTaskTitle() + "\n"
//                + "\n\nPlease make sure to complete it on time.";
//
//        EmailService emailService;
//
//        // Sending email using the EmailService
//        emailService.sendEmail(task.getUser().getEmail(), subject, body);
//    }
}
