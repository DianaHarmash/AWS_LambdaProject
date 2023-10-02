package com.example.diplomaspringproject1_0.validators.interfaces;


import com.example.diplomaspringproject1_0.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public interface Validator<T> {
    void validate(T value) throws UserException;
}
