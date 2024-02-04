package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/speciality")
public class SpecialityController {

    private final SpecialityService specialityService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SpecialityDto> createSpeciality(@RequestBody SpecialityDto specialityDto) throws UserException {
        return new ResponseEntity<>(specialityService.createSpeciality(specialityDto),
                                    HttpStatus.CREATED);
    }
    @GetMapping("/display/{speciality}")
    public ResponseEntity<SpecialityDto> getSpecialityBuSpecialityName(@PathVariable String speciality) {
        Optional<SpecialityDto> specialityDto = specialityService.getSpecialityBySpecialityName(speciality);

        return new ResponseEntity<>(specialityDto.orElseThrow(), HttpStatus.FOUND);
    }
    @PutMapping("/{speciality}")
    public ResponseEntity<SpecialityDto> updateSpeciality(@PathVariable String speciality,
                                                          @RequestBody SpecialityDto specialityDto) {
        return new ResponseEntity<>(specialityService.updateSpeciality(speciality, specialityDto),
                                    HttpStatus.OK);
    }
    @DeleteMapping ("/{speciality}")
    public void deleteSpeciality(@PathVariable String speciality) throws UserException {
        specialityService.deleteSpeciality(speciality);
    }
}
