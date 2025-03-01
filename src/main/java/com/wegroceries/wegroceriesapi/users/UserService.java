package com.wegroceries.wegroceriesapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user with a specified role.
     *
     * @param user The user to register.
     * @param roleName The name of the role to assign to the user.
     * @return The registered user.
     * @throws IllegalArgumentException If the username or email already exists, or if the role is invalid.
     */
    @Transactional
    public User registerUser(User user, String roleName) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Fetch or create the Role entity
        Role role = roleRepository.findByName(ERole.valueOf(roleName))
                                  .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        // Assign the role to the user
        user.getRoles().add(role);

        // Save the user to the database
        return userRepository.save(user);
    }

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user.
     * @return The user.
     * @throws IllegalArgumentException If the user is not found.
     */
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * Get a user by their username.
     *
     * @param username The username of the user.
     * @return An Optional containing the user if found.
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get all users.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update a user's details.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The updated user details.
     * @return The updated user.
     * @throws IllegalArgumentException If the user is not found.
     */
    @Transactional
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = getUserById(id);

        // Update fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        // Only update password if a new one is provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws IllegalArgumentException If the user is not found.
     */
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Check if a username already exists.
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Check if an email already exists.
     *
     * @param email The email to check.
     * @return True if the email exists, false otherwise.
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Create a user (if needed).
     *
     * @param user The user to create.
     * @return The created user.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    public User createUser(User user) {
        // Implement this method if needed, or remove it
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
}