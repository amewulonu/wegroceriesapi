package com.wegroceries.wegroceriesapi.users;

import com.wegroceries.wegroceriesapi.exception.InvalidUserDataException;
import com.wegroceries.wegroceriesapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getEmail() == null) {
            throw new InvalidUserDataException("Username and Email are required.");
        }
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get a user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found."));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        if (user.getUsername() == null || user.getEmail() == null) {
            throw new InvalidUserDataException("Username and Email cannot be empty.");
        }
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Check if a username exists
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> usernameExists(@PathVariable String username) {
        boolean exists = userService.usernameExists(username);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Check if an email exists
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
