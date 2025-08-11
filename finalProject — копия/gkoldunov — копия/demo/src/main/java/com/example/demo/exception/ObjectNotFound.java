package com.example.demo.exception;

public class ObjectNotFound extends RuntimeException {
    public ObjectNotFound(String objectName) {
        super("Object was not found: " + objectName);

    }
}
