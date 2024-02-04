package com.example.diplomaspringproject1_0.validators;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.enums.CodeOfOperation;
import com.example.diplomaspringproject1_0.exceptions.ApiError;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
