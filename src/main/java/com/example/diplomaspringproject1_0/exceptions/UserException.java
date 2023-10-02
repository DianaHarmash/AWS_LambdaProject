package com.example.diplomaspringproject1_0.exceptions;

public class UserException extends Exception {

    private final ApiError apiError;

    public UserException(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
