package com.example.diplomaspringproject1_0.entity;

import com.example.diplomaspringproject1_0.entity.enums.Speciality;
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
public class SpecialityPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column(nullable = false)
    private BigDecimal price;
}
