package com.example.diplomaspringproject1_0.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleCustomException(UserException e) {
        return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
    }
}
