package com.annak.handcrafted.controller;

import com.annak.handcrafted.exception.BadRequestException;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.exception.ResourceUniqueViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ResourceUniqueViolationException.class)
    public ErrorResponse handleResourceUniqueViolationException(ResourceUniqueViolationException ex) {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleResourceUniqueViolationException(BadRequestException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
