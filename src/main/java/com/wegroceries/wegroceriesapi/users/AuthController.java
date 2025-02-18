package com.wegroceries.wegroceriesapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);  // Call register service to save user
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);  // Respond with created status
    }

    // Login (can be extended for JWT-based login)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        // You can add JWT-based authentication here
        return ResponseEntity.ok("Login successful");
    }
}

