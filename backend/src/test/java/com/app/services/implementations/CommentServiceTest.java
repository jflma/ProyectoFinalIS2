package com.app.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.domain.post.Answer;
import com.app.domain.post.Comment;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.repositories.CommentRepositoryImp;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @Mock
  private UserService userService;
  @Mock
  private PostService postService;
  @Mock
  private AnswerService answerService;
  @Mock
  private CommentRepositoryImp commentRepository;
  @InjectMocks
  private CommentService commentService;

  @Test
  void testPostComement () {
    Long idPost = 1L;
    Long idUser = 1L;
    String content = "comentario";

    Post post = new Post();
    post.setId(idPost);

    ForoUser user = new ForoUser();
    user.setId(idUser);

    Comment comment = new Comment();
    comment.setUser(user);
    comment.setContent(content);

    when(postService.getPostById(idPost)).thenReturn(post);
    when(userService.getUserbyId(idUser)).thenReturn(user);
    when(commentRepository.save(any(Comment.class))).thenReturn(comment);

    Comment commentCreated = commentService.postComment(idPost, idUser, content);

    assertNotNull(commentCreated);
    assertEquals(content, commentCreated.getContent());
  }

  @Test
  void testAnswerComment () {
    Long idAnswer = 1L;
    Long idUser = 1L;
    String content = "comentario";

    Answer answer = new Answer();
    answer.setId(idAnswer);

    ForoUser user = new ForoUser();
    user.setId(idUser);

    Comment comment = new Comment();
    comment.setUser(user);
    comment.setContent(content);

    when(answerService.getAnswerById(idAnswer)).thenReturn(answer);
    when(userService.getUserbyId(idUser)).thenReturn(user);
    when(commentRepository.save(any(Comment.class))).thenReturn(comment);

    Comment commentCreated = commentService.answerComment(idAnswer, idUser, content);
    
    assertNotNull(commentCreated);
    assertEquals(content, commentCreated.getContent());
  }

}
