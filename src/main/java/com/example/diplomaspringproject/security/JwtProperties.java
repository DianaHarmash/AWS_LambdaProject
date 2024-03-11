package com.example.diplomaspringproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Configuration
//@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtProperties {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.lifetime}")
    private Duration duration;

    public String jwtToken(long id, String email, List<String> roles) {
        Date date = new Date();
        Date expiredAt = new Date(date.getTime() + duration.toMillis());

        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", email)
                .withClaim("authority", roles)
                .withIssuedAt(date)
                .withExpiresAt(expiredAt)
                .sign(Algorithm.HMAC256(secretKey));
    }
}
