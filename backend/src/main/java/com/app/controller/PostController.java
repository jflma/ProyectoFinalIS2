package com.app.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.dto.CreatePostFieldsDTO;
import com.app.controller.dto.response.PostDetailsDTO;
import com.app.controller.dto.response.PostPreviewDTO;
import com.app.domain.post.Post;
import com.app.services.implementations.PostService;
import com.app.services.interfaces.IPostService;


@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/post")
public class PostController { 

  private IPostService postService;

  public PostController (PostService postService) {
    this.postService = postService;

  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/create")
  public Post createPost(@RequestBody CreatePostFieldsDTO fields) {
    return postService.createPost(fields.idUser(),fields.title(),fields.content());
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/ultimatePost")
  public List<PostPreviewDTO> getUltimatePost () {
    return postService.getUltimatePost();
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/details/{idPost}")
  public PostDetailsDTO getDetailsPostById (@PathVariable Long idPost) {
    return  postService.getDetailsPostById(idPost);
  }

}
