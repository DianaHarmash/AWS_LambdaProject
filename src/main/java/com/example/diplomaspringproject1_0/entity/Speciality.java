package com.example.diplomaspringproject1_0.entity;

import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
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
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    @Enumerated(EnumType.STRING)
    private SpecialityName speciality;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "quantity_of_places",
            nullable = false)
    private Integer quantityOfPlaces;

    @Column(name = "quantity_of_billable_places",
            nullable = false)
    private Integer quantityOfBillablePlaces;
}
