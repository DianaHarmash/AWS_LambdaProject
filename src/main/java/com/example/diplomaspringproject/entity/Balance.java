package com.example.diplomaspringproject.entity;

import com.example.diplomaspringproject.entity.enums.CodeOfOperation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal sum;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CodeOfOperation codeOfOperation;

    private BigDecimal balance;
}
