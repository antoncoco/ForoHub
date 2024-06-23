package com.antoncoco.forohub.infrastructure.errors;

import org.springframework.validation.FieldError;

public record InvalidFieldError(String field, String message) {
    public InvalidFieldError(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
