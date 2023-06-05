package com.metagain.backend.exception;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class, NoFriendsException.class})
    public ResponseEntity<ApiError> handleViolationException(Exception e) {
        log.error("An exception occurred", e);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), 400, "Validation error");

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(Exception e) {
        log.error("An exception occurred", e);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, e.getMessage(), 409, "Resource already exists");

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleEmptyResultDataAccessException(Exception e) {
        log.error("An exception occurred", e);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), 404, "Resource not found");

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAnyException(Exception e) {
        log.error("An exception occurred", e);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), 500, "An error occurred");

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
