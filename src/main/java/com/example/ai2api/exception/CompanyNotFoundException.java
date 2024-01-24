package com.example.ai2api.exception;


public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String id) {
        super("Could not find company with id " + id);
    }

}