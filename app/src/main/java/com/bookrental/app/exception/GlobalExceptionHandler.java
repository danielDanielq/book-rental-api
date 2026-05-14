package com.bookrental.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Note: Error in one of the controller? This will catch it and send it in a JSON format;
public class GlobalExceptionHandler{

    @ExceptionHandler(EmailAlreadyInUseException.class) // Note: This will automaticly catch the EmailAlreadyInUse exception;
    public ResponseEntity<Map<String, String>> handleEmailAlreadyInUse(EmailAlreadyInUseException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", message)); // Note: We want to respond with a key-value Map so that the Client(Postman/Frontend) gets a nice JSON;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()) // Note: Construct the map, example .getField() = "password" and .getDefaultMessage() = "Password size must be...";
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResouceNotFound(ResouceNotFoundException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message));
    }

    @ExceptionHandler(DateOutOfBoundsException.class)
    public ResponseEntity<Map<String, String>> handleExceptionDateOutOfBounds(Exception exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleExceptionAccessDenied(AccessDeniedException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", message));
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyBoundException(BookAlreadyReturnedException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", message));
    }

    @ExceptionHandler(NotAllowedToUpdateException.class)
    public ResponseEntity<Map<String, String>> handleNotAllowedToUpdateException(NotAllowedToUpdateException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", message));
    }

}
