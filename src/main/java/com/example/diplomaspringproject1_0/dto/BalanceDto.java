package com.example.diplomaspringproject1_0.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDto {

    private Long id;

    private BigDecimal balance;

}
