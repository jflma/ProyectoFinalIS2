package com.app.modules.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.app.BaseIntegrationTest;
import com.app.exceptions.UnauthorizedException;
import com.app.modules.auth.controller.dto.LoginRequestDTO;
import com.app.modules.auth.controller.dto.SignupFieldsDTO;
import com.app.modules.auth.controller.dto.response.TokenResponse;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Person;
import com.app.modules.user.service.IUserService;

public class AuthControllerTest extends BaseIntegrationTest {

    @MockBean
    private IUserService userService;

    @Test
    void testSigninSuccess() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("testuser", "password");
        TokenResponse mockToken = TokenResponse.builder().token("mocked-jwt-token").build();

        when(userService.loginUser(any(LoginRequestDTO.class))).thenReturn(mockToken);

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.type").value("Bearer"));
    }

    @Test
    void testSigninFailure() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("invalid", "invalid");

        when(userService.loginUser(any(LoginRequestDTO.class)))
                .thenThrow(new UnauthorizedException("Invalid credentials"));

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Expect 401
    }

    @Test
    void testSignupSuccess() throws Exception {
        SignupFieldsDTO signupFields = new SignupFieldsDTO(
                "John", "Doe", "john@example.com", LocalDate.of(1990, 1, 1),
                "johndoe", "password123");

        Person mockPerson = Person.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        ForoUser mockUser = ForoUser.builder()
                .id(1L)
                .username("johndoe")
                .person(mockPerson)
                .roles(Collections.emptySet())
                .build();

        when(userService.registerUser(any(SignupFieldsDTO.class))).thenReturn(mockUser);

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupFields)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
