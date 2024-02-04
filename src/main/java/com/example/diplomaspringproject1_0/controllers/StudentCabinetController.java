package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.StudentCabinetService;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/students")
public class StudentCabinetController {

    private final StudentCabinetService studentCabinetService;

    @PutMapping("/update")
    public ResponseEntity<StudentCabinetDto> updateStudentCabinetForUser(@RequestBody StudentCabinetDto systemUserDto) throws UserException {
        return new ResponseEntity<>(studentCabinetService.updateStudentCabinet(systemUserDto),
                                    HttpStatus.CREATED);
    }
    @GetMapping("/display")
    public ResponseEntity<StudentCabinetDto> getStudentCabinet(@RequestParam(required = false) String email) {
        Optional<StudentCabinetDto> studentCabinetDto = studentCabinetService.getStudentCabinet(email);
        return new ResponseEntity<>(studentCabinetDto.orElseThrow(), HttpStatus.FOUND);
    }

    @GetMapping ("/balance")
    public ResponseEntity<String> transitPayToTheBalance(@RequestParam String email) {
        return studentCabinetService.transitPayToTheBalance(email);
    }

}
