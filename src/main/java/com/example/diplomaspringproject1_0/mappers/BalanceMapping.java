package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.entity.enums.CodeOfOperation;
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

        balance.setSum(source.getCodeOfOperation().getPayment());
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
                balance.setBalance(source.getCodeOfOperation().getPayment());
            }

        }

        return balance;
    }

}