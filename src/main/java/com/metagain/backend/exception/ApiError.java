package com.metagain.backend.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private String message;

    private int code;

    private List<String> errors;
    public ApiError(HttpStatus status, String message, int code, List<String> errors) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, int code, String error) {
        super();
        this.status = status;
        this.message = message;
        this.code = code;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public List<String> getErrors() {
        return errors;
    }
}
