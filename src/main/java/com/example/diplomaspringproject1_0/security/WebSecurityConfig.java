package com.example.diplomaspringproject1_0.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .cors().disable() // Cross-Origin Resource Sharing
                .csrf().disable() // Cross-Site Request Forgery
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Some authentication mechanisms like HTTP Basic are stateless and, therefore, re-authenticates the user on every request.
                .and()
                .formLogin().disable() // disable web foem of authentication
                .securityMatcher("/**")
                .authorizeHttpRequests(registery -> {
                    registery.requestMatchers("/auth/**").permitAll() // for '/auth/**' all requests are permitted
                            .anyRequest().authenticated(); // for all other requests authentication is
                });

        return http.build();
    }
}
