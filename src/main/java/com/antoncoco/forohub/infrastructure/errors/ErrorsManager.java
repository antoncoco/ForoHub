package com.antoncoco.forohub.infrastructure.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorsManager {
    @ExceptionHandler(ObjectAlreadyPersisted.class)
    public ResponseEntity<String> errorHandlerObjectAlreadyExists(ObjectAlreadyPersisted exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidFieldError>> errorHandlerMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<InvalidFieldError> invalidFieldErrors = exception.getFieldErrors().stream().map(InvalidFieldError::new).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidFieldErrors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> errorHandlerEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
}
