package com.example.diplomaspringproject1_0.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Deanery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary_expenses",
            nullable = false)
    private BigDecimal expensesForSalaries;

    @Column(name = "room_expenses",
            nullable = false)
    private BigDecimal expensesForRoom;

    @Column(name = "other_expenses",
            nullable = false)
    private BigDecimal expensesForOtherStuff;

    @Column(name = "students_income")
    private BigDecimal incomeFromStudents;

    @Column(name = "quote_from_government",
            nullable = false)
    private BigDecimal quoteFromGovernment;
}
