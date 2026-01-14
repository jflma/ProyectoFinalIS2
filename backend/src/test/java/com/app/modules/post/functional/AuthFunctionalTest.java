package com.app.modules.post.functional;

import com.app.modules.auth.controller.dto.SignupFieldsDTO;
import com.app.modules.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService; // To clean up or verify if needed

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        // Given a new user payload
        SignupFieldsDTO newUser = new SignupFieldsDTO(
                "testUserFunc",
                "password123",
                "Test",
                "User",
                "testuserfunc@example.com",
                "1990-01-01");

        // When performing a POST to /auth/signup
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                // Then expect 201 Created
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFailToRegisterDuplicateUser() throws Exception {
        // Given an existing user payload (reusing the one above or creating first)
        SignupFieldsDTO newUser = new SignupFieldsDTO(
                "duplicateUser",
                "password123",
                "Duplicate",
                "User",
                "duplicate@example.com",
                "1990-01-01");

        // Initial registration
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());

        // When trying to register again
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                // Then expect error (Logic might vary, assuming Bad Request or Conflict)
                .andExpect(status().isBadRequest());
    }
}
