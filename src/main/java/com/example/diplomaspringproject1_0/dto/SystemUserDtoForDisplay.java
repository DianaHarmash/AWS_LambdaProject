package com.example.diplomaspringproject1_0.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SystemUserDtoForDisplay {

    private Long id;

    private String surname;

    private String name;

    private String email;
}
