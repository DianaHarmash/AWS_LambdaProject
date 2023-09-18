package com.example.diplomaspringproject1_0.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cource_id")
    private Course course;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "is_paid",
            nullable = false)
    private Boolean isPaid;

}
