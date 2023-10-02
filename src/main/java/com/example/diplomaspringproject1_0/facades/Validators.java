package com.example.diplomaspringproject1_0.facades;

import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.validators.UserValidators;
import com.example.diplomaspringproject1_0.validators.interfaces.Validator;
import org.springframework.stereotype.Component;

@Component
public class Validators {
    public Validator getValidators(Entities entity) {
        switch (entity) {
            case USER: return new UserValidators();
            // TODO: add other instances;
            default: return null;
        }
    }
}
