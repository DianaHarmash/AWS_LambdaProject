package com.example.diplomaspringproject1_0.dto;

import com.example.diplomaspringproject1_0.entity.enums.CodeOfOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class BalanceDto {

    private Long id;

    private Date date;

    private BigDecimal sum;

    private CodeOfOperation codeOfOperation;

    private BigDecimal balance;
}
