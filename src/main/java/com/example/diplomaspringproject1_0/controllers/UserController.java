package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.SystemUsersDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SystemUsersDto> createUser(@RequestBody SystemUsersDto systemUsersDto) throws UserException {
        return new ResponseEntity<>(userService.createUser(systemUsersDto), HttpStatus.OK);
    }

    @GetMapping("/main")
    public String getDeaneryInformation() {
        return "Hello, World!";
    }

}
