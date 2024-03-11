package com.example.diplomaspringproject.security;

import com.example.diplomaspringproject.dto.SystemUserDto;
import com.example.diplomaspringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SystemUserDto userDto = userService.findByEmail(email).orElseThrow();

        return UserPrincipal.builder()
                .userId(userDto.getId())
                .email(email)
                .password(userDto.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(userDto.getRights())))
                .build();
    }
}
