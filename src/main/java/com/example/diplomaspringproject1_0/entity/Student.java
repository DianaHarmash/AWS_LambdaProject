package com.example.diplomaspringproject1_0.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, unique = true)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(name = "passport_series",
            nullable = false)
    private Integer passportSeries;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "group_name",
            nullable = false)
    private String groupName;

    @Column(nullable = false)
    private String speciality;

}
