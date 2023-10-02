package com.example.diplomaspringproject1_0.entity;

import com.example.diplomaspringproject1_0.entity.enums.Rights;
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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SystemUser user;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "group_name",
            nullable = false)
    private String groupName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_price_id", referencedColumnName = "id")
    private SpecialityPrice speciality;

    @Column(name = "debt_balance",
            nullable = false)
    private BigDecimal debtBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rights rights;
}
