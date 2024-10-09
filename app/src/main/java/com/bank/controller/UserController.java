package com.bank.controller;

import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.saveUser(user);
        return ResponseEntity.ok("User saved successfully!");
    }

    @GetMapping("/{documentNumber}")
    public ResponseEntity<User> getUser(@PathVariable String documentNumber) {
        User user = userRepository.getUserByDocumentNumber(documentNumber);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
