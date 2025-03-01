package com.wegroceries.wegroceriesapi.users;

import com.wegroceries.wegroceriesapi.exception.InvalidUserDataException;
import com.wegroceries.wegroceriesapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Create a new user (Only Admin can create users).
     *
     * @param user The user to create.
     * @return The created user.
     * @throws InvalidUserDataException If the username or email is missing.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidUserDataException("Username is required.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidUserDataException("Email is required.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidUserDataException("Password is required.");
        }

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get a user by ID (Admin or the user themselves).
     *
     * @param id The ID of the user.
     * @return The user.
     * @throws UserNotFoundException If the user is not found.
     */
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Get a user by username (Only Admin).
     *
     * @param username The username of the user.
     * @return The user.
     * @throws UserNotFoundException If the user is not found.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found."));
    }

    /**
     * Get all users (Only Admin).
     *
     * @return A list of all users.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Update a user (Admin or the user themselves).
     *
     * @param id The ID of the user to update.
     * @param user The updated user details.
     * @return The updated user.
     * @throws InvalidUserDataException If the username or email is missing.
     * @throws UserNotFoundException If the user is not found.
     */
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidUserDataException("Username is required.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidUserDataException("Email is required.");
        }

        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Delete a user (Only Admin).
     *
     * @param id The ID of the user to delete.
     * @return A response with no content.
     * @throws UserNotFoundException If the user is not found.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Check if a username exists (Anyone can access).
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> usernameExists(@PathVariable String username) {
        boolean exists = userService.usernameExists(username);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    /**
     * Check if an email exists (Anyone can access).
     *
     * @param email The email to check.
     * @return True if the email exists, false otherwise.
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}