package com.example.diplomaspringproject.dto;

import com.example.diplomaspringproject.entity.enums.CodeOfOperation;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BalanceDto {

    private Long id;

    private Date date;

    private BigDecimal sum;

    private CodeOfOperation codeOfOperation;

    private BigDecimal balance;

    public BalanceDto(BigDecimal sum,
                      Date date,
                      CodeOfOperation codeOfOperation) {
        this.sum = sum;
        this.date = date;
        this.codeOfOperation = codeOfOperation;
    }

//    public BalanceDto(CodeOfOperation codeOfOperation) {
//        this.codeOfOperation = codeOfOperation;
//    }
}
