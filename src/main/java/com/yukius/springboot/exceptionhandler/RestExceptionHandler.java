package com.yukius.springboot.exceptionhandler;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<?> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    /**@throws EntityNotFoundException */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNullPointerException(EntityNotFoundException entityNotFoundException) {

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, entityNotFoundException);
        return buildResponseEntity(apiError);
    }
    /**@throws DataIntegrityViolationException */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, dataIntegrityViolationException);
        apiError.setMessage(dataIntegrityViolationException.getRootCause().getMessage());

        return buildResponseEntity(apiError);
    }
    /**@throws NullPointerException */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(NullPointerException nullPointerException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, nullPointerException);
        apiError.setMessage("Please enter valid new password");
        return buildResponseEntity(apiError);
    }


}
