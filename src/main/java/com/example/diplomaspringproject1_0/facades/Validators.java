package com.example.diplomaspringproject1_0.facades;

import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.validators.*;
import com.example.diplomaspringproject1_0.validators.interfaces.Validator;
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
