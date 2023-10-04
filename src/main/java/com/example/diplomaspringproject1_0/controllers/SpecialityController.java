package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/speciality")
public class SpecialityController {

    private final SpecialityService specialityService;
    @Autowired
    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SpecialityDto> createSpeciality(@RequestParam Long adminId, @RequestBody SpecialityDto specialityDto) {
        return new ResponseEntity<>(specialityService.createSpeciality(adminId, specialityDto),
                                    HttpStatus.CREATED);
    }
    @GetMapping("/{specialityString}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SpecialityDto> getSpecialityBuSpecialityName(@PathVariable String specialityString) {
        return new ResponseEntity<>(specialityService.getSpecialityBuSpecialityName(specialityString),
                                    HttpStatus.OK);
    }
}
