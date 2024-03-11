package com.example.diplomaspringproject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginRequest {

    private String email;

    private String password;
}
