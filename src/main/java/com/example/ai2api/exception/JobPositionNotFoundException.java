package com.example.ai2api.exception;

public class JobPositionNotFoundException extends RuntimeException {
    public JobPositionNotFoundException(String id) {
        super("Could not find job position with id " + id);
    }

}