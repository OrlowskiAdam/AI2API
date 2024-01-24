package com.example.ai2api.exception;

public class SalaryMustBePositive extends RuntimeException {
    public SalaryMustBePositive() {
        super("Salary must be positive");
    }

}