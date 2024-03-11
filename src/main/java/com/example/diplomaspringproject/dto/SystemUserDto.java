package com.example.diplomaspringproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SystemUserDto {

    private Long id;

    private String surname;

    private String name;

    private String email;

    private String password;

    private String rights;

}
