package com.matekeszi.authentication.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<Object> handleException(final UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .errorMessage(ex.getMessage())
                                .build());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleException(final HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .errorMessage("Request body is incorrect!")
                                .build());
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleException(final EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .errorMessage(ex.getMessage())
                                .build());
    }

    @ExceptionHandler(value = SecurityException.class)
    protected ResponseEntity<Object> handleException(final SecurityException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .errorMessage(ex.getMessage())
                                .build());
    }
}
