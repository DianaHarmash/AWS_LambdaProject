package com.example.diplomaspringproject.facades;

import com.example.diplomaspringproject.entity.enums.Entities;
import com.example.diplomaspringproject.validators.*;
import com.example.diplomaspringproject.validators.interfaces.Validator;
import org.springframework.stereotype.Component;

@Component
public class Validators {
    public Validator getValidators(Entities entity) {
        switch (entity) {
            case USER: return new UserValidators();
            case STUDENT_CABINET: return new UserCabinetValidators();
            case BALANCE: return new BalanceValidators();
            case SPECIALITY: return new SpecialityValidators();
            case BALANCE_FIRST_RECORD: return new BalanceValidatorsForFirstRecord();
            default: return null;
        }
    }
}
