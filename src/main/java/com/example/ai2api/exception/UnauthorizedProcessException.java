package com.example.ai2api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedProcessException extends RuntimeException {
    public UnauthorizedProcessException() {
        super("Unauthorized process exception " + HttpStatus.UNAUTHORIZED);
    }
}
