package com.example.ai2api.utils;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.ai2api.exception.*;
import com.example.ai2api.payload.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleCompanyNotFoundException(CompanyNotFoundException exception) {
        return new ResponseEntity<>(new ErrorMessageDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        return new ResponseEntity<>(new ErrorMessageDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobPositionNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleRoleNotFoundException(JobPositionNotFoundException exception) {
        return new ResponseEntity<>(new ErrorMessageDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SalaryMustBePositive.class)
    public ResponseEntity<ErrorMessageDto> handleSalaryMustBePositiveException(SalaryMustBePositive exception) {
        return new ResponseEntity<>(new ErrorMessageDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailBusyException.class)
    public ResponseEntity<ErrorMessageDto> handleEmailBusyException(EmailBusyException exception) {
        return new ResponseEntity<>(new ErrorMessageDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorMessageDto> tokenExpired(TokenExpiredException ex) {
        return new ResponseEntity<>(new ErrorMessageDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedProcessException.class)
    public ResponseEntity<?> handleUnauthorizedProcessException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        constraintViolationSet.forEach(constraintViolation -> {
            LinkedList<String> path = new LinkedList<>();
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                path.add(node.getName());
            }
            String errorMessage = constraintViolation.getMessage();
            errors.put(path.getLast(), errorMessage);
        });
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> getErrors(String fieldName, String errorMessage) {
        Map<String, String> errors = new HashMap<>();
        errors.put(fieldName, errorMessage);
        return errors;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleRequestPartExceptions(MissingServletRequestParameterException ex) {
        Map<String, String> errors = getErrors(ex.getParameterName(), ex.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPartExceptions(MissingServletRequestPartException ex) {
        Map<String, String> errors = getErrors(ex.getRequestPartName(), ex.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
