package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.service.StudentCabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/students")
public class StudentCabinetController {

    private final StudentCabinetService studentCabinetService;
    @Autowired
    public StudentCabinetController(StudentCabinetService studentCabinetService) {
        this.studentCabinetService = studentCabinetService;
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<StudentCabinetDto> updateStudentCabinetForUser(@RequestParam Long adminId,
                                                                         @RequestBody StudentCabinetDto systemUserDto) {
        return new ResponseEntity<>(studentCabinetService.updateStudentCabinet(adminId, systemUserDto),
                                    HttpStatus.CREATED);
    }

    @DeleteMapping("{/id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteStudentCabinet() {

        // TODO: delete student cabinet

    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> transitPayToTheBalance() {

        // TODO: add transaction between a student and the university

        return null;
    }

}
