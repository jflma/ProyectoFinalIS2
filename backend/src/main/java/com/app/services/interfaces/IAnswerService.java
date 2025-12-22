package com.app.services.interfaces;

import com.app.domain.post.Answer;

public interface IAnswerService {

  public Answer createAnswer(Long postIdTtoReply,String content);

  public Answer getAnswerById(Long idAnswer);

}