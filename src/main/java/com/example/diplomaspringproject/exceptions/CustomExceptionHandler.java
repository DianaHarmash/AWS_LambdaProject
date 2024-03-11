package com.example.diplomaspringproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleCustomException(UserException e) {
        return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(ApiError.builder()
                                            .message("System does not contain such element.")
                                            .userMessage("Шуканий елемент не знайдено.")
                                            .statusCode(HttpStatus.NOT_FOUND)
                                            .build(), HttpStatus.NOT_FOUND);
    }
}
