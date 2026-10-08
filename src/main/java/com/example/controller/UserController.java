package com.example.controller;


import com.example.entity.User;
import com.example.entity.UserStatus;
import com.example.service.UserService;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam @Size(max = 20) String name,
                                           @RequestParam @Size(max = 12) String contactNumber,
                                           @RequestParam @Size(max = 50) String email,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth
    ) {
        User createdUser = userService.createUser(name, contactNumber, email, dateOfBirth);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,
                                           @RequestParam(required = false) @Size(max = 20) String name,
                                           @RequestParam(required = false) @Size(max = 12) String contactNumber,
                                           @RequestParam(required = false) @Size(max = 50) String email) {
        User updatedUser = userService.updateUser(id, name, contactNumber, email);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkUser(@PathVariable long id) {
        boolean isAvailable = userService.isUserActive(id);
        return ResponseEntity.ok(isAvailable);

    }
}
