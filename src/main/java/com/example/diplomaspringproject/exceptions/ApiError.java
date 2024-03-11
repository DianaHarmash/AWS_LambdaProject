package com.example.diplomaspringproject.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError {
    private HttpStatus statusCode;
    private String message;
    private String userMessage;
}
