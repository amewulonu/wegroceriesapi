package com.wegroceries.wegroceriesapi.users;

import com.wegroceries.wegroceriesapi.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Handle the root /api/auth endpoint
    @GetMapping
    public ResponseEntity<String> authRoot() {
        logger.info("Accessed the root /api/auth endpoint");
        return ResponseEntity.ok("Welcome to the Auth API!");
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        logger.info("Registering new user: {}", user.getUsername());

        // Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set timestamps (if not set by the client)
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(Instant.now());
        }
        if (user.getUpdatedAt() == null) {
            user.setUpdatedAt(Instant.now());
        }

        // Register the user
        User newUser = userService.registerUser(user, "defaultRole");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Login with JWT-based authentication
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt by user: {}", loginRequest.getUsername());

        // Validate credentials
        User user = userService.getUserByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Invalid username: {}", loginRequest.getUsername());
                    return new IllegalArgumentException("Invalid username or password.");
                });

        // Check if the password matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            logger.warn("Invalid password for user: {}", loginRequest.getUsername());
            throw new IllegalArgumentException("Invalid username or password.");
        }

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getUsername());
        logger.info("User {} logged in successfully", loginRequest.getUsername());

        // Return the token and user details in a response object
        AuthResponse authResponse = new AuthResponse(token, user.getUsername(), user.getEmail());
        return ResponseEntity.ok(authResponse);
    }

    // Inner class for login response
    public static class AuthResponse {
        private String token;
        private String username;
        private String email;

        public AuthResponse(String token, String username, String email) {
            this.token = token;
            this.username = username;
            this.email = email;
        }

        // Getters and setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // Inner class for login request
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
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
    }
}