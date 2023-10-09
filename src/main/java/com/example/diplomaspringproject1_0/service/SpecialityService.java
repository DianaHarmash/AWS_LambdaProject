package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import com.example.diplomaspringproject1_0.mappers.SpecialityMapping;
import com.example.diplomaspringproject1_0.repositories.SpecialtyRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.diplomaspringproject1_0.mappers.SpecialityMapping.transformSpecialityNameToEnum;

@Service
@Slf4j
public class SpecialityService {

    private final SpecialityMapping specialityMapping;
    private final SpecialtyRepository specialtyRepository;
    @Autowired
    public SpecialityService(SpecialityMapping specialityMapping,
                             SpecialtyRepository specialtyRepository) {
        this.specialityMapping = specialityMapping;
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional
    public SpecialityDto createSpeciality(Long adminId, SpecialityDto specialityDto) {
        // TODO: check admin rights
        log.debug("Starting creating speciality = {}", specialityDto.getSpeciality());

        // TODO: add validation for speciality name

        Speciality specialityToSave = specialityMapping.specialityDtoToSpeciality(specialityDto);
        Speciality speciality = specialtyRepository.save(specialityToSave);
        SpecialityDto savedSpeciality = specialityMapping.specialityToSpecialityDto(speciality);

        log.debug("Created speciality with name = {}", savedSpeciality.getSpeciality());

        return savedSpeciality;
    }

    public SpecialityDto getSpecialityBuSpecialityName(String requestedSpeciality) {
        log.debug("Getting speciality for name = {}", requestedSpeciality);

        SpecialityName specialityName = transformSpecialityNameToEnum(requestedSpeciality);

        Speciality speciality = specialtyRepository.findBySpeciality(specialityName)
                                                   .orElseThrow();

        log.debug("Retrieving speciality with id = {}", speciality.getId());
        SpecialityDto specialityDto = specialityMapping.specialityToSpecialityDto(speciality);
        return specialityDto;
    }
}
