package com.example.diplomaspringproject1_0.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtConfiguration {
    private final JwtProperties properties;

    public String jwtToken(long id, String email, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", email)
                .withClaim("authority", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
