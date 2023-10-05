package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SystemUserDto> createUser(@RequestBody SystemUserDto systemUsersDto) throws UserException {
        return new ResponseEntity<>(userService.createUser(systemUsersDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SystemUserDto> getUserById(@PathVariable Long id) {
        // TODO: add retrieving information about user

        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SystemUserDto> updateUser(@PathVariable Long id,
                                                    @RequestBody SystemUserDto systemUserDto) {
        // TODO: add updating of the user

        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {
        // TODO: delete the user

    }

}
