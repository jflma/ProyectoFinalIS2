package com.app.controller.dto.response;

import java.time.LocalDateTime;

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
public class PostDetailsDTO {
  private Long id;
  private Long idEntry;
  private String title;

  private String content;
  private LocalDateTime createdAt;

  private String username;

}
