package com.example.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(users);
    }
}
