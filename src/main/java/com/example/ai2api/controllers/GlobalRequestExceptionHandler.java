package com.example.ai2api.controllers;

import com.example.ai2api.exception.ApiError;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = "com.example.ai2api.controllers")
public class GlobalRequestExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<Object> handleDeserializationException(InvalidFormatException ex) {
        String fieldNames = ex.getPath()
                .stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining(", "));
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, String.format("Invalid value '%s' for field '%s'.", ex.getValue(), fieldNames), ex));
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = {JsonMappingException.class})
//    protected ResponseEntity<ErrorResponse> handleMethodArgNotValidException(JsonMappingException  ex, Locale locale) {
//        String errorMessage = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
//                .collect(Collectors.joining("; "));
//        // put errorMessage into ErrorResponse
//        ErrorResponse er = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, errorMessage).build();
//        return ResponseEntity.badRequest().body(er);
//    }
//}