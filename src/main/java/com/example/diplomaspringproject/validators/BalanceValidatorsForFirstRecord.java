package com.example.diplomaspringproject.validators;

import com.example.diplomaspringproject.dto.BalanceDto;
import com.example.diplomaspringproject.entity.enums.CodeOfOperation;
import com.example.diplomaspringproject.exceptions.ApiError;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BalanceValidatorsForFirstRecord implements Validator<BalanceDto> {
    @Override
    public void validate(BalanceDto balanceDto) throws UserException {
        if (!(balanceDto.getCodeOfOperation().equals(CodeOfOperation.INCOME_FROM_STUDENTS) ||
                balanceDto.getCodeOfOperation().equals(CodeOfOperation.QUOTE_FROM_GOVERNMENT))) {
            throw new UserException(ApiError.builder()
                    .message("No funds in the university's account.")
                    .userMessage("Університету не вистачає коштів.")
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }
}
