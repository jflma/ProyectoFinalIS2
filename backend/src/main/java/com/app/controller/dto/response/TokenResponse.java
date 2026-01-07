package com.app.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@SuppressWarnings("unused")
public class TokenResponse {
  private String token;
}
