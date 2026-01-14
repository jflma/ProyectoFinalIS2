package com.app.modules.post.functional;

import com.app.modules.auth.controller.dto.SignupFieldsDTO;
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

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        SignupFieldsDTO newUser = new SignupFieldsDTO(
                "Test",
                "User",
                "testuserfunc" + System.currentTimeMillis() + "@example.com", // Unique email
                java.time.LocalDate.parse("1990-01-01"),
                "testUserFunc" + System.currentTimeMillis(), // Unique username
                "password123");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFailToRegisterDuplicateUser() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        SignupFieldsDTO newUser = new SignupFieldsDTO(
                "Duplicate",
                "User",
                "duplicate" + uniqueSuffix + "@example.com",
                java.time.LocalDate.parse("1990-01-01"),
                "duplicateUser" + uniqueSuffix,
                "password123");

        // Initial registration
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());

        // When trying to register again
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isBadRequest());
    }
}
