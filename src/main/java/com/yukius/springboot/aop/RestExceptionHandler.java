package com.yukius.springboot.aop;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    /**EntityNotFoundException Handler */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNullPointerException(EntityNotFoundException entityNotFoundException) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED,"Please enter a correct login and password");
        return buildResponseEntity(apiError);
    }
    /**DataIntegrityViolationException Handler */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, dataIntegrityViolationException);
        return buildResponseEntity(apiError);
    }
    /**NullPointerException Handler */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(NullPointerException nullPointerException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Please enter valid new password");
        return buildResponseEntity(apiError);
    }


}
