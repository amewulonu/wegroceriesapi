package com.wegroceries.wegroceriesapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.wegroceries.wegroceriesapi.exception.InvalidUserDataException;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }
    // Handle Invalid User Data Exception
    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUserDataException(InvalidUserDataException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid Input");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
