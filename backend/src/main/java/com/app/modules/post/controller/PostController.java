package com.app.modules.post.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PostDetailsDTO;
import com.app.dto.PostResponseDTO;
import com.app.modules.post.controller.dto.CreatePostFieldsDTO;
import com.app.modules.post.domain.Post;
import com.app.modules.post.service.IPostService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/post")
public class PostController {

  private IPostService postService;

  public PostController(IPostService postService) {
    this.postService = postService;
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/create")
  public ResponseEntity<PostDetailsDTO> createPost(@RequestBody CreatePostFieldsDTO fields) {
    Post post = postService.createPost(fields.idUser(), fields.title(), fields.content());

    if (post == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    PostDetailsDTO response = PostDetailsDTO.builder()
        .id(post.getId())
        .title(post.getTitle())
        .views(post.getViews())
        .answers(post.getAnswers())
        .authorUsername(
            post.getEntry() != null && post.getEntry().getUser() != null ? post.getEntry().getUser().getUsername()
                : null)
        .createdAt(post.getEntry() != null ? post.getEntry().getCreatedAt() : null)
        .content(post.getEntry() != null ? post.getEntry().getContent() : null)
        .upVotes(post.getEntry() != null ? post.getEntry().getUpVotes() : 0)
        .downVotes(post.getEntry() != null ? post.getEntry().getDownVotes() : 0)
        .commentsCount(post.getEntry() != null ? post.getEntry().getComments() : 0)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/ultimatePost")
  public ResponseEntity<List<PostResponseDTO>> getUltimatePost() {
    List<com.app.modules.post.controller.dto.response.PostPreviewDTO> oldList = postService.getUltimatePost();

    List<PostResponseDTO> responseList = oldList.stream()
        .map(old -> PostResponseDTO.builder()
            .id(old.getId())
            .title(old.getTitle())
            .views(old.getViews())
            .answers(old.getAnswers())
            // Fields not available in old DTO are left as null/default
            .build())
        .collect(Collectors.toList());

    return ResponseEntity.ok(responseList);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/details/{idPost}")
  public ResponseEntity<PostDetailsDTO> getDetailsPostById(@PathVariable Long idPost) {
    com.app.modules.post.controller.dto.response.PostDetailsDTO oldDetails = postService.getDetailsPostById(idPost);

    if (oldDetails == null) {
      return ResponseEntity.notFound().build();
    }

    PostDetailsDTO response = PostDetailsDTO.builder()
        .id(oldDetails.getId())
        .title(oldDetails.getTitle())
        .content(oldDetails.getContent())
        .authorUsername(oldDetails.getUsername())
        .createdAt(oldDetails.getCreatedAt())
        .idEntry(oldDetails.getIdEntry())
        .build();

    return ResponseEntity.ok(response);
  }

}
