package com.app.modules.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

import com.app.modules.post.domain.Entry;
import com.app.modules.post.domain.Post;
import com.app.modules.user.domain.ForoUser;
import com.app.modules.user.service.UserService;
import com.app.modules.post.persistence.PostRepositoryImp;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @Mock
  private EntryService entryService;
  @Mock
  private PostRepositoryImp postRepository;
  @Mock
  private UserService userService;

  @InjectMocks
  private PostService postService;

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
  void testCreatePost() {
    Long idUser = 1L;
    String title = "titulo";
    String content = "content";
    String username = "tester";
    ForoUser user = new ForoUser();
    user.setUsername(username);
    user.setId(idUser);
    Entry entry = new Entry();
    entry.setUser(user);
    entry.setContent(content);

    Post post = new Post();
    post.setEntry(entry);

    lenient().when(authentication.getPrincipal()).thenReturn(username);
    when(userService.getUserByUsername(username)).thenReturn(user);
    when(entryService.createEntry(user, content)).thenReturn(entry);
    when(postRepository.save(any(Post.class))).thenReturn(post);

    Post postCreated = postService.createPost(idUser, title, content);

    assertNotNull(postCreated);
    assertEquals(idUser, postCreated.getEntry().getUser().getId());
  }

  @Test
  @Transactional
  void testGetPostById() {
    Long idPost = 1L;
    Post post = new Post();
    post.setId(idPost);

    when(postRepository.findById(idPost)).thenReturn(Optional.of(post));

    Post postFound = postService.getPostById(idPost);

    assertNotNull(postFound);
    assertEquals(idPost, postFound.getId());

  }

}
