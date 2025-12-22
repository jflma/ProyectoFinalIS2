package com.app.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.domain.post.Post;
import com.app.services.implementations.PostService;
import com.app.services.interfaces.IPostService;

@CrossOrigin("http://localhost:3000/")
@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/search")
public class SearchController {

  private IPostService postService;

  public SearchController (PostService postService){
    this.postService=postService;
  }

  @GetMapping("/posts")
  public List<Post> searchWord (@RequestParam("keyword") String keyword) {
    return postService.searchWord(keyword);
  }
  @GetMapping("/posts/index")
  public boolean index () {
    return postService.index();
  }


}
