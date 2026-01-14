package com.app.modules.post.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PostResponseDTO;
import com.app.modules.post.domain.Post;

import com.app.modules.post.service.IPostService;

@CrossOrigin("http://localhost:3000/")
@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/search")
public class SearchController {

  private IPostService postService;

  public SearchController(IPostService postService) {
    this.postService = postService;
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostResponseDTO>> searchWord(@RequestParam("keyword") String keyword) {
    List<Post> posts = postService.searchWord(keyword);

    List<PostResponseDTO> response = posts.stream()
        .map(post -> PostResponseDTO.builder()
            .id(post.getId())
            .title(post.getTitle())
            .views(post.getViews())
            .answers(post.getAnswers())
            .authorUsername(
                post.getEntry() != null && post.getEntry().getUser() != null ? post.getEntry().getUser().getUsername()
                    : null)
            .createdAt(post.getEntry() != null ? post.getEntry().getCreatedAt() : null)
            .build())
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/posts/index")
  public ResponseEntity<Boolean> index() {
    return ResponseEntity.ok(postService.index());
  }

}
