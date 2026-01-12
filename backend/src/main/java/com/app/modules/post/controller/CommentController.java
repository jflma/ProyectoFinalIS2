package com.app.modules.post.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.modules.post.controller.dto.CommentPostFieldsDTO;
import com.app.modules.post.controller.dto.response.CommentDetailsDTO;
import com.app.modules.post.domain.Comment;
import com.app.modules.post.service.CommentService;
import com.app.modules.post.service.ICommentService;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/comment")
public class CommentController {

  private ICommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/getComments/{idEntry}")
  public List<CommentDetailsDTO> getCommentsFromEntry(@PathVariable Long idEntry) {
    return commentService.getCommentsFromPost(idEntry);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/post")
  public Comment commentPost(@RequestBody CommentPostFieldsDTO fields) {
    return commentService.postComment(fields.idPost(), fields.idUser(), fields.content());
  }
}
