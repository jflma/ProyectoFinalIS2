package com.app.modules.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AnswerResponseDTO;
import com.app.modules.post.controller.dto.CreateAnswerFieldsDTO;
import com.app.modules.post.domain.Answer;
import com.app.modules.post.service.AnswerService;
import com.app.modules.post.service.IAnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
  private IAnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping
  public ResponseEntity<AnswerResponseDTO> createAnswerForPost(@RequestBody CreateAnswerFieldsDTO fieldsDTO) {
    Answer answer = answerService.createAnswer(fieldsDTO.postId(), fieldsDTO.content());

    if (answer == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    AnswerResponseDTO response = AnswerResponseDTO.builder()
        .id(answer.getId())
        .content(answer.getEntry() != null ? answer.getEntry().getContent() : null)
        .authorUsername(
            answer.getEntry() != null && answer.getEntry().getUser() != null ? answer.getEntry().getUser().getUsername()
                : null)
        .createdAt(answer.getEntry() != null ? answer.getEntry().getCreatedAt() : null)
        .postId(answer.getPost() != null ? answer.getPost().getId() : null)
        .upVotes(answer.getEntry() != null ? answer.getEntry().getUpVotes() : 0)
        .downVotes(answer.getEntry() != null ? answer.getEntry().getDownVotes() : 0)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
