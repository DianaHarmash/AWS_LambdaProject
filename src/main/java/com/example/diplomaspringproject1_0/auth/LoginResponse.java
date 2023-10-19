package com.example.diplomaspringproject1_0.auth;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginResponse {

    private String token;
}
