package com.app.modules.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.app.modules.post.service.AnswerService;
import com.app.modules.post.service.EntryService;
import com.app.modules.post.service.PostService;
import com.app.modules.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.app.modules.post.domain.Answer;
import com.app.modules.post.domain.Entry;
import com.app.modules.post.domain.Post;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.post.persistence.AnswerRepositoryImp;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private PostService postService;
    @Mock
    private EntryService entryService;
    @Mock
    private AnswerRepositoryImp answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @Transactional
    void testCreateAnswer() {
        // given que es lo que necesito tener para testear el metodo
        String username = "testerUser";
        Long postIdToReply = 1L;
        String content = "Content de respuesta";

        ForoUser user = new ForoUser();
        user.setUsername(username);

        Entry entry = new Entry();
        entry.setContent(content);

        Post post = new Post();
        post.setId(postIdToReply);

        Answer answer = new Answer();
        answer.setEntry(entry);
        answer.setPost(post);

        // when

        lenient().when(authentication.getPrincipal()).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(entryService.createEntry(user, content)).thenReturn(entry);
        when(postService.getPostById(postIdToReply)).thenReturn(post);
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);// provamos que hac

        // then

        // Act
        Answer answerCreated = answerService.createAnswer(postIdToReply, content);

        // Assert

        assertNotNull(answerCreated);
    }

    @Test
    @Transactional
    void testGetAnswerById() {
        Long idAnswer = 1L;

        Answer answer = new Answer();
        answer.setId(idAnswer);

        // when
        when(answerRepository.findById(idAnswer)).thenReturn(Optional.of(answer));

        // Act
        Answer answerFound = answerService.getAnswerById(idAnswer);

        // Assert
        assertNotNull(answerFound);
        assertEquals(idAnswer, answerFound.getId());
    }

}