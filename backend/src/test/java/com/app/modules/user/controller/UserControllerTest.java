package com.app.modules.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.app.BaseIntegrationTest;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Person;
import com.app.modules.user.domain.Role;
import com.app.modules.user.service.IUserService;

public class UserControllerTest extends BaseIntegrationTest {

    @MockBean
    private IUserService userService;

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testRoleAdmin() throws Exception {
        mockMvc.perform(get("/user/role"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi you has role ADMIN"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void testRoleAdminForbidden() throws Exception {
        mockMvc.perform(get("/user/role"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testHelloWorld() throws Exception {
        mockMvc.perform(get("/user/helloworld"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world !"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void testCheckStatus() throws Exception {
        Person person = Person.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        Role role = new Role();
        role.setName("USER");

        ForoUser user = ForoUser.builder()
                .id(1L)
                .username("testuser")
                .person(person)
                .roles(Set.of(role))
                .build();

        when(userService.getUserByUsername("testuser")).thenReturn(user);

        mockMvc.perform(get("/user/check-status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }

    @Test
    void testCheckStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/user/check-status"))
                .andExpect(status().isUnauthorized()); // Or 403 depending on config, but unauthenticated should be
                                                       // 401/403
    }
}
