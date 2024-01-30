package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.LoginRequest;
import com.example.diplomaspringproject1_0.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject1_0.service.AuthService;
import com.example.diplomaspringproject1_0.dto.LoginResponse;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.UserService;
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
