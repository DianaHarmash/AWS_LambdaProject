package com.example.diplomaspringproject.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginResponse {

    private String token;
}
