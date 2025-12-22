package com.app.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CreationException.class)
  public ResponseEntity<ErrorDetails> handleCreationException(CreationException ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder().timeStamp(new Date()).message(ex.getMessage())
        .details(request.getDescription(false)).build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorDetails> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder().timeStamp(new Date()).message(ex.getMessage())
        .details(request.getDescription(false)).build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

}
