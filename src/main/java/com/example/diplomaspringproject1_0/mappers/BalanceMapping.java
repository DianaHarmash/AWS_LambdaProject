package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.entity.enums.CodeOfOperation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapping {

    BalanceDto balanceToBalanceDto(Balance source);
    default Balance balanceDtoToBalance(BalanceDto source) {
        Balance balance = new Balance();

        if (source.getId() != null) {
            balance.setId(source.getId());
        }

        balance.setSum(source.getSum());
        balance.setCodeOfOperation(source.getCodeOfOperation());
        balance.setDate(source.getDate());

        if (source.getCodeOfOperation().equals(CodeOfOperation.SALARY) ||
            source.getCodeOfOperation().equals(CodeOfOperation.ROOM) ||
            source.getCodeOfOperation().equals(CodeOfOperation.STUFF)) {
            balance.setBalance(source.getBalance().subtract(source.getSum()));
        } else {
            if (source.getBalance() != null) {
                balance.setBalance(source.getBalance().add(source.getSum()));
            }
            else {
                balance.setBalance(source.getSum());
            }

        }

        return balance;
    }

}