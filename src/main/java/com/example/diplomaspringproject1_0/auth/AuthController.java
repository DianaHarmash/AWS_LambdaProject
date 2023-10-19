package com.example.diplomaspringproject1_0.auth;

import com.example.diplomaspringproject1_0.security.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtConfiguration jwtConfiguration;

    @PostMapping("/login")
    public LoginResponse authUser(@RequestBody LoginRequest loginRequest) {
        String token = jwtConfiguration.jwtToken(1L, loginRequest.getEmail(), List.of("USER"));

        return LoginResponse.builder()
                            .token(token)
                            .build();
    }
}
