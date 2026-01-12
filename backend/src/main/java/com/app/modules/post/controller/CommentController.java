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

import com.app.dto.CommentResponseDTO;
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
  @GetMapping("/entry/{idEntry}")
  public ResponseEntity<List<CommentResponseDTO>> getCommentsFromEntry(@PathVariable Long idEntry) {
    List<CommentDetailsDTO> oldList = commentService.getCommentsFromPost(idEntry);

    // Map existing DTO to new standardized DTO
    List<CommentResponseDTO> responseList = oldList.stream()
        .map(old -> CommentResponseDTO.builder()
            .id(old.getId())
            .authorUsername(old.getAutorUsername()) // Fixing typo from old DTO
            .content(old.getContent())
            .build())
        .collect(Collectors.toList());

    return ResponseEntity.ok(responseList);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping
  public ResponseEntity<CommentResponseDTO> commentPost(@RequestBody CommentPostFieldsDTO fields) {
    Comment comment = commentService.postComment(fields.idPost(), fields.idUser(), fields.content());

    if (comment == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    CommentResponseDTO response = CommentResponseDTO.builder()
        .id(comment.getId())
        .authorUsername(comment.getUser() != null ? comment.getUser().getUsername() : null)
        .content(comment.getContent())
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
