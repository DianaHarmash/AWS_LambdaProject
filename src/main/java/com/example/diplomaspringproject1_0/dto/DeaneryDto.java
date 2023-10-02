package com.example.diplomaspringproject1_0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeaneryDto {

    private Long id;

    @JsonProperty("salaries_expenses")
    private BigDecimal expensesForSalaries;

    @JsonProperty("room_expenses")
    private BigDecimal expensesForRoom;

    @JsonProperty("other_expenses")
    private BigDecimal expensesForOtherStuff;

    @JsonProperty("student_income")
    private BigDecimal incomeFromStudents;

    @JsonProperty("government_quote")
    private BigDecimal quoteFromGovernment;
}
