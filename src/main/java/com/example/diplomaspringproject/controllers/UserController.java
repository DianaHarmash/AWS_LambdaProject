package com.example.diplomaspringproject.controllers;

import com.example.diplomaspringproject.dto.LoginRequest;
import com.example.diplomaspringproject.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject.service.AuthService;
import com.example.diplomaspringproject.dto.LoginResponse;
import com.example.diplomaspringproject.dto.SystemUserDto;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<LoginResponse> createUser(@RequestBody SystemUserDto systemUsersDto) throws UserException {
        userService.createUser(systemUsersDto);
        return new ResponseEntity<>(authService.attemptLogin(systemUsersDto.getEmail(), systemUsersDto.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<SystemUserDtoForDisplay> getUserByEmail(@PathVariable String email) {
        Optional<SystemUserDtoForDisplay> systemUserDto = userService.getUserByEmail(email);
        return new ResponseEntity<>(systemUserDto.orElseThrow(), HttpStatus.FOUND);
    }

    @GetMapping("/token")
    public LoginResponse getToken(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }

    @PatchMapping("/{email}")
    public ResponseEntity<SystemUserDtoForDisplay> updateUser(@PathVariable String email,
                                                              @RequestBody SystemUserDto systemUserDto) {
        return new ResponseEntity<>(userService.updatingUserByEmail(email, systemUserDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

}
