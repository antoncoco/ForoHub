package com.antoncoco.forohub.infrastructure.errors;

public class ObjectAlreadyPersisted extends RuntimeException {
    public ObjectAlreadyPersisted(String message) {
        super(message);
    }
}
