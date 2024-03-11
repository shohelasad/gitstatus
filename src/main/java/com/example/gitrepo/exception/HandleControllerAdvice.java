package com.example.gitrepo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@ControllerAdvice
public class HandleControllerAdvice extends RuntimeException {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorMessages> resourceAccessFoundException(ResourceAccessException ex, WebRequest request) {
        ErrorMessages errorMessage = ErrorMessages.builder()
                .statusCode(HttpStatus.REQUEST_TIMEOUT.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description( request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessages> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorMessages errorMessage = ErrorMessages.builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessages> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessages errorMessage = ErrorMessages.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description( request.getDescription(false))
                .build();


        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
