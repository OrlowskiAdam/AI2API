package com.example.ai2api.exception;

public class NameBusyException extends RuntimeException {
    public NameBusyException(String name) {
        super("Name is busy " + name);
    }
}
