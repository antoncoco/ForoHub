package com.antoncoco.forohub.infrastructure.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsManager {
    @ExceptionHandler(ObjectAlreadyPersisted.class)
    public ResponseEntity<String> errorHandlerObjectAlreadyExists(ObjectAlreadyPersisted exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
