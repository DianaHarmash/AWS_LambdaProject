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
public class StudentCabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SystemUser user;

    @Column
    private Integer year;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_price_id", referencedColumnName = "id")
    private Speciality speciality;

    @Column(name = "debt_balance")
    private BigDecimal debtBalance;
}
