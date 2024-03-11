package com.example.diplomaspringproject.exceptions;

public class UserException extends Exception {

    private final ApiError apiError;

    public UserException(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
