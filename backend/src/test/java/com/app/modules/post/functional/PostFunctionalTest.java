package com.app.modules.post.functional;

import com.app.modules.auth.controller.dto.SignupFieldsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private com.app.modules.user.service.UserService userService;

    @Test
    @WithMockUser(username = "testUserPost", roles = "USER")
    public void shouldCreatePostSuccessfully() throws Exception {
        // Ensure the user 'testUserPost' exists in the DB because PostService retrieves
        // it
        // from SecurityContext (which matches WithMockUser) via username.
        String contextUsername = "testUserPost";

        try {
            userService.getUserByUsername(contextUsername);
        } catch (Exception e) {
            // If not found, create it
            userService.registerUser(new SignupFieldsDTO(
                    "Test", "User", contextUsername + "@mail.com", java.time.LocalDate.now(), contextUsername,
                    "password"));
        }

        // We get the ID just for the payload, although the service ignores it (it uses
        // context).
        Long userId = userService.getUserByUsername(contextUsername).getId();

        String postJson = String.format("""
                {
                    "idUser": %d,
                    "title": "Cómo solucionar error en Java?",
                    "content": "Tengo un NullPointerException..."
                }
                """, userId);

        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson))
                .andExpect(status().isCreated());
    }
}
