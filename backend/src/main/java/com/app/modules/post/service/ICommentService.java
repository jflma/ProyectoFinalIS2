package com.app.modules.post.service;

import java.util.List;

import com.app.controller.dto.response.CommentDetailsDTO;
import com.app.modules.post.domain.Comment;

public interface ICommentService {
  public Comment postComment(Long postId, Long userId, String content);

  public Comment answerComment(Long answerId, Long userId, String content);

  public List<CommentDetailsDTO> getCommentsFromPost(Long idEntry);
}
