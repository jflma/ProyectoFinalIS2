package com.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
    private Long postId;
    private int upVotes;
    private int downVotes;
}
