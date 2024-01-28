package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
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
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudentCabinetDto> updateStudentCabinetForUser(@RequestBody StudentCabinetDto systemUserDto) {
        return new ResponseEntity<>(studentCabinetService.updateStudentCabinet(systemUserDto),
                                    HttpStatus.CREATED);
    }
    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudentCabinetDto> getStudentCabinet(@RequestParam(required = false) Long id,
                                                               @RequestParam(required = false) String surname,
                                                               @RequestParam(required = false) String name) {
        Optional<StudentCabinetDto> studentCabinetDto = studentCabinetService.getStudentCabinet(id, surname, name);
        return new ResponseEntity<>(studentCabinetDto.orElseThrow(), HttpStatus.FOUND);
    }

    @GetMapping ("/balance")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> transitPayToTheBalance(@RequestParam String surname,
                                                         @RequestParam String name) {
        return studentCabinetService.transitPayToTheBalance(surname, name);
    }

}
