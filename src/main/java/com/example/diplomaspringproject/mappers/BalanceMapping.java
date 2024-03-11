package com.example.diplomaspringproject.mappers;

import com.example.diplomaspringproject.dto.BalanceDto;
import com.example.diplomaspringproject.entity.Balance;
import com.example.diplomaspringproject.entity.enums.CodeOfOperation;
import org.mapstruct.Mapper;

import java.sql.Date;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface BalanceMapping {

    BalanceDto balanceToBalanceDto(Balance source);
    default Balance balanceDtoToBalance(BalanceDto source) {
        Balance balance = new Balance();

        if (source.getId() != null) {
            balance.setId(source.getId());
        }

        if (source.getCodeOfOperation().equals(CodeOfOperation.INCOME_FROM_STUDENTS)) {
            balance.setSum(source.getSum());
        } else {
            balance.setSum(source.getCodeOfOperation().getPayment());
        }
        balance.setCodeOfOperation(source.getCodeOfOperation());
        balance.setDate(Date.valueOf(LocalDate.now()));

        if (source.getCodeOfOperation().equals(CodeOfOperation.SALARY) ||
            source.getCodeOfOperation().equals(CodeOfOperation.ROOM) ||
            source.getCodeOfOperation().equals(CodeOfOperation.STUFF)) {
            balance.setBalance(source.getBalance().subtract(source.getCodeOfOperation().getPayment()));
        } else {
            if (source.getBalance() != null) {
                balance.setBalance(source.getBalance().add(source.getSum()));
                balance.setSum(source.getSum());
            }
            else {
                balance.setBalance(source.getSum());
            }
        }

        return balance;
    }

}