package com.example.diplomaspringproject1_0.security;

import com.example.diplomaspringproject1_0.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig   {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    private final CustomUserDetailService userDetailService;

    private final UserService userService;

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors().disable() // Cross-Origin Resource Sharing
                .csrf().disable() // Cross-Site Request Forgery
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Some authentication mechanisms like HTTP Basic are stateless and, therefore, re-authenticates the user on every request.
                .and()
                .formLogin().disable() // disable web foem of authentication
                .securityMatcher("/**")
                .authorizeHttpRequests(registery -> {
                    registery.requestMatchers("/users").permitAll() // permitted to all
                             .requestMatchers("/users/token").permitAll()
                             .requestMatchers("/auth/forAdmin").hasRole("ADMIN")
                             .anyRequest().authenticated(); // for all other requests authentication is
                })
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        return http.build();
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(PasswordEncoder.getPasswordEncoder())
                .and()
                .build();
    }
}
