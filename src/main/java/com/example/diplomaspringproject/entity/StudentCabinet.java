package com.example.diplomaspringproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SystemUser user;

    @Column
    private Integer year;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Speciality speciality;

    @Column(name = "debt_balance")
    private BigDecimal debtBalance;
}
