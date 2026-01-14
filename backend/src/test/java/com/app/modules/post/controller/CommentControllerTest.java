package com.app.modules.post.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.app.BaseIntegrationTest;
import com.app.modules.post.controller.dto.CommentPostFieldsDTO;
import com.app.modules.post.controller.dto.response.CommentDetailsDTO;
import com.app.modules.post.domain.Comment;
import com.app.modules.post.domain.Entry;
import com.app.modules.post.service.ICommentService;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.domain.Person;

public class CommentControllerTest extends BaseIntegrationTest {

    @MockBean
    private ICommentService commentService;

    @Test
    void testGetCommentsByEntrySuccess() throws Exception {
        CommentDetailsDTO details = new CommentDetailsDTO(1L, "user1", "Comment Content");

        when(commentService.getCommentsFromPost(1L)).thenReturn(List.of(details));

        mockMvc.perform(get("/comment/entry/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Comment Content"))
                .andExpect(jsonPath("$[0].authorUsername").value("user1"));
    }

    @Test
    @WithMockUser(username = "user1", roles = { "USER" })
    void testCreateCommentSuccess() throws Exception {
        CommentPostFieldsDTO fields = new CommentPostFieldsDTO(1L, 10L, "New Comment");

        Person person = Person.builder().firstName("John").lastName("Doe").build();
        ForoUser user = ForoUser.builder().id(1L).username("user1").person(person).build();

        Entry entry = Entry.builder().id(10L).build();

        Comment comment = new Comment();
        comment.setId(100L);
        comment.setContent("New Comment");
        comment.setUser(user);
        comment.setEntry(entry);

        when(commentService.postComment(anyLong(), anyLong(), anyString())).thenReturn(comment);

        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("New Comment"))
                .andExpect(jsonPath("$.authorUsername").value("user1"));
    }

    @Test
    void testCreateCommentUnauthorized() throws Exception {
        CommentPostFieldsDTO fields = new CommentPostFieldsDTO(1L, 10L, "New Comment");

        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isUnauthorized()); // Assuming 401/403
    }
}
