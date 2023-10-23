package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.auth.AuthService;
import com.example.diplomaspringproject1_0.auth.LoginResponse;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LoginResponse> createUser(@RequestBody SystemUserDto systemUsersDto) throws UserException {
        SystemUserDto userDto = userService.createUser(systemUsersDto);
        return new ResponseEntity<>(authService.attemptLogin(userDto.getEmail(), userDto.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ResponseEntity<SystemUserDto> getUserById(@PathVariable Long id) {
        Optional<SystemUserDto> systemUserDto = userService.getUserById(id);
        return new ResponseEntity<>(systemUserDto.orElseThrow(), HttpStatus.FOUND);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SystemUserDto> updateUser(@PathVariable Long id,
                                                    @RequestBody SystemUserDto systemUserDto) {
        return new ResponseEntity<>(userService.updatingUserById(id, systemUserDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id,
                               @RequestParam Long adminId) {
        userService.deleteUserById(adminId, id);
    }

}
