package com.example.diplomaspringproject.entity.enums;

import java.math.BigDecimal;

public enum CodeOfOperation {
    SALARY(new BigDecimal(540000)),
    ROOM(new BigDecimal(12000)),
    STUFF(new BigDecimal(50000)),
    INCOME_FROM_STUDENTS,
    QUOTE_FROM_GOVERNMENT(new BigDecimal(120000));

    private BigDecimal payment;

    CodeOfOperation(BigDecimal payment) {
        this.payment = payment;
    }

    CodeOfOperation() {}

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}
