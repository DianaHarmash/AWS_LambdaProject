package com.example.diplomaspringproject.controllers;

import com.example.diplomaspringproject.dto.LoginRequest;
import com.example.diplomaspringproject.dto.LoginResponse;
import com.example.diplomaspringproject.security.UserPrincipal;
import com.example.diplomaspringproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse authUser(@RequestBody LoginRequest loginRequest) {
        return authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/secure")
    public String secure(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "If you see it, you're logged in  as user " + userPrincipal.getEmail() + ".\n" +
                "User ID: " + userPrincipal.getUserId() +  ".\n" +
                "User role: " + Arrays.toString(List.of(userPrincipal.getAuthorities()).toArray()) + ".\n";
    }

    @GetMapping("/forAdmin")
    public String testAdminRoles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "If you see it, you are an ADMIN! + User id: " + userPrincipal.getUserId();
    }

    @GetMapping("/info")
    public String getUser(Principal principal) {
        return principal.getName();
    }
}
