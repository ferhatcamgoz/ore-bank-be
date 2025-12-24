package com.banking.oredata.exception;

import com.banking.oredata.base.BaseApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
            .badRequest()
            .body(new BaseApiResponse(false, message, null));
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<BaseApiResponse> handleAuthException(JwtAuthenticationException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new BaseApiResponse(false, ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new BaseApiResponse(false, "Access denied", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseApiResponse> handleOtherExceptions(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new BaseApiResponse(false, "An unknown error occurred", null));
    }
}
