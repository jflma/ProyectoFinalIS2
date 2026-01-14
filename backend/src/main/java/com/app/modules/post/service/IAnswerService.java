package com.app.modules.post.service;

import com.app.modules.post.domain.Answer;

public interface IAnswerService {

  public Answer createAnswer(Long postIdTtoReply, String content);

  public Answer getAnswerById(Long idAnswer);

}