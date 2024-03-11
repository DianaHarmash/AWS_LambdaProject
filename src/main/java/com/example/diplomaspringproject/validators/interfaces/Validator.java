package com.example.diplomaspringproject.validators.interfaces;


import com.example.diplomaspringproject.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public interface Validator<T> {
    void validate(T value) throws UserException;
}
