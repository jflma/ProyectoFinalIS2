package com.app.modules.post.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  @PostMapping("/create")
  public Answer createAnswerForPost(@RequestBody CreateAnswerFieldsDTO fieldsDTO) {
    return answerService.createAnswer(fieldsDTO.postId(), fieldsDTO.content());

  }

}
