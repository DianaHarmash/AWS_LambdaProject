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
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<StudentCabinetDto> createStudentCabinetForUser(@RequestParam Long adminId,
                                                                        @RequestBody StudentCabinetDto systemUserDto) {
        return new ResponseEntity<>(studentCabinetService.createStudentCabinet(adminId, systemUserDto),
                                    HttpStatus.CREATED);
    }

}
