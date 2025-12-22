package com.app.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class ErrorDetails {
  private Date timeStamp;
  private String message;
  private String details;
}
