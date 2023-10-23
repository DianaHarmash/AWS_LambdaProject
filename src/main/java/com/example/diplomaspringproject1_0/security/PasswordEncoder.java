package com.example.diplomaspringproject1_0.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static BCryptPasswordEncoder getPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

}
