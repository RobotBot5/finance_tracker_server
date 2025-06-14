package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.ApiError;
import com.robotbot.finance_tracker_server.domain.exceptions.EmailAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuilder details = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ")
        );

        ApiError apiError = ApiError.builder()
                .title("Validation Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(details.toString())
                .instance(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .title("Email Already In Use")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}

