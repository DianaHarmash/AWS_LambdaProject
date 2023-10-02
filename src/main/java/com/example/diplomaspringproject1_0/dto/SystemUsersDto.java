package com.example.diplomaspringproject1_0.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SystemUsersDto {

    private Long id;

    private String surname;

    private String name;

    private String rights;

}
