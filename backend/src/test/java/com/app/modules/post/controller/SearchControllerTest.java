package com.app.modules.post.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.app.BaseIntegrationTest;
import com.app.modules.post.domain.Post;
import com.app.modules.post.service.IPostService;

public class SearchControllerTest extends BaseIntegrationTest {

    @MockBean
    private IPostService postService;

    @Test
    void testSearchPostsSuccess() throws Exception {
        Post post = Post.builder().id(1L).title("Test Post").build();

        when(postService.searchWord("Test")).thenReturn(List.of(post));

        mockMvc.perform(get("/search/posts")
                .param("keyword", "Test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Post"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testSearchIndexSuccess() throws Exception {
        when(postService.index()).thenReturn(true);

        mockMvc.perform(get("/search/posts/index")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
