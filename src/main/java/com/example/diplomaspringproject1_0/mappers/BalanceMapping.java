package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface BalanceMapping {

    BalanceDto balanceToBalanceDto(Balance source);
    Balance balanceDtoToBalance(BalanceDto source);

}