package com.example.diplomaspringproject1_0.entity;

import com.example.diplomaspringproject1_0.entity.enums.Rights;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,
            unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rights rights;
}
