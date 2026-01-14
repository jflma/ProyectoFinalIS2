package com.app.modules.post.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.app.BaseIntegrationTest;
import com.app.modules.post.controller.dto.CreateAnswerFieldsDTO;
import com.app.modules.post.domain.Answer;
import com.app.modules.post.domain.Entry;
import com.app.modules.post.domain.Post;
import com.app.modules.post.service.IAnswerService;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Person;

public class AnswerControllerTest extends BaseIntegrationTest {

    @MockBean
    private IAnswerService answerService;

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void testCreateAnswerSuccess() throws Exception {
        CreateAnswerFieldsDTO fields = new CreateAnswerFieldsDTO(1L, "This is an answer");

        Person person = Person.builder().firstName("John").lastName("Doe").build();
        ForoUser user = ForoUser.builder().id(2L).username("user").person(person).build();

        Post post = Post.builder().id(1L).title("Test Post").build();

        Entry entry = Entry.builder()
                .id(10L)
                .content("This is an answer")
                .user(user)
                .createdAt(LocalDateTime.now())
                .upVotes(0)
                .downVotes(0)
                .build();

        Answer answer = Answer.builder()
                .id(100L)
                .entry(entry)
                .post(post)
                .build();

        when(answerService.createAnswer(anyLong(), anyString())).thenReturn(answer);

        mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.content").value("This is an answer"))
                .andExpect(jsonPath("$.postId").value(1));
    }

    @Test
    void testCreateAnswerUnauthorized() throws Exception {
        CreateAnswerFieldsDTO fields = new CreateAnswerFieldsDTO(1L, "This is an answer");

        mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isUnauthorized()); // Assuming 401/403 behavior
    }
}
