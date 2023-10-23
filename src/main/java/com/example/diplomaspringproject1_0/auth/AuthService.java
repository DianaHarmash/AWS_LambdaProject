package com.example.diplomaspringproject1_0.auth;

import com.example.diplomaspringproject1_0.security.JwtConfiguration;
import com.example.diplomaspringproject1_0.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtConfiguration jwtConfiguration;

    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();

        List<String> roles = principal.getAuthorities().stream()
                                      .map(GrantedAuthority::getAuthority)
                                      .toList();

        String token = jwtConfiguration.jwtToken(principal.getUserId(), principal.getEmail(), roles);

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
