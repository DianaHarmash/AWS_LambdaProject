package com.example.diplomaspringproject1_0.dto;

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
