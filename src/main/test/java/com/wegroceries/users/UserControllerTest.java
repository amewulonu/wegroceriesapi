package com.wegroceries.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegroceries.wegroceriesapi.users.User;
import com.wegroceries.wegroceriesapi.users.UserController;
import com.wegroceries.wegroceriesapi.users.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
    }

    // Test case: Create User Successfully
    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    // Test case: Create User - Bad Request
    @Test
    void testCreateUser_BadRequest() throws Exception {
        when(userService.createUser(any())).thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    // Test case: Get User By ID - Success
    @Test
    void testGetUserById_Success() throws Exception {
        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    // Test case: Get User By ID - Not Found
    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(userId)).thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    // Test case: Get All Users
    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test case: Update User - Success
    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.updateUser(any(), any())).thenReturn(user);

        mockMvc.perform(put("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    // Test case: Update User - Not Found
    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.updateUser(any(), any())).thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(put("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    // Test case: Delete User - Success
    @Test
    void testDeleteUser_Success() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());
    }

    // Test case: Delete User - Not Found
    @Test
    void testDeleteUser_NotFound() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("User not found")).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    // Test case: Username Exists - True
    @Test
    void testUsernameExists_True() throws Exception {
        when(userService.usernameExists("john_doe")).thenReturn(true);

        mockMvc.perform(get("/api/users/exists/username/john_doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // Test case: Username Exists - False
    @Test
    void testUsernameExists_False() throws Exception {
        when(userService.usernameExists("john_doe")).thenReturn(false);

        mockMvc.perform(get("/api/users/exists/username/john_doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    // Test case: Email Exists - True
    @Test
    void testEmailExists_True() throws Exception {
        when(userService.emailExists("john@example.com")).thenReturn(true);

        mockMvc.perform(get("/api/users/exists/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // Test case: Email Exists - False
    @Test
    void testEmailExists_False() throws Exception {
        when(userService.emailExists("john@example.com")).thenReturn(false);

        mockMvc.perform(get("/api/users/exists/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
