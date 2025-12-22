package com.app.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SuppressWarnings("unused")
public class PostPreviewDTO {
  private Long id;
  private String title;
  private int views;
  private int answers;
  private String content;
  private int upVotes;
  private int downVotes;
}
