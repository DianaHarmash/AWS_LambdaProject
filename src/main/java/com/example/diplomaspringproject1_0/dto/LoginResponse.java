package com.example.diplomaspringproject1_0.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginResponse {

    private String token;
}
