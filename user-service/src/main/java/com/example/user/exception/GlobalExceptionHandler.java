package com.example.user.exception;

import com.example.user.model.error.ErrorResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_OCCURRED = "An error occurred: ";

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .error(ex.getLocalizedMessage())
            .message("User not found")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUserException.class)
    ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.CONFLICT.value())
            .error(ex.getLocalizedMessage())
            .message("User already exists")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JwtException.class)
    ResponseEntity<ErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .error(ex.getLocalizedMessage())
            .message("Wrong JWT")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) {
        log.error(ERROR_OCCURRED, ex);

        var response = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(ex.getLocalizedMessage())
            .message("Server error")
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
