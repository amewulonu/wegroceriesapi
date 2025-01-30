package com.wegroceries.wegroceriesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<List<String>> handleValidationException(BindingResult result) {
    
    // Collecting error messages for field validation failures
        List<String> errorMessages = result.getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    return fieldName + ": " + error.getDefaultMessage();
                })
                .collect(Collectors.toList());

    // Return a BAD_REQUEST status with error messages
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Handle custom exceptions (e.g., resource not found, invalid data)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle validation errors for incorrect input (e.g., invalid data types)
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

