package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import com.example.diplomaspringproject1_0.mappers.SpecialityMapping;
import com.example.diplomaspringproject1_0.repositories.SpecialtyRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<SpecialityDto> getSpecialityBySpecialityName(String requestedSpeciality) {
        log.debug("Getting speciality for name = {}", requestedSpeciality);

        SpecialityName specialityName = transformSpecialityNameToEnum(requestedSpeciality);

        Optional<Speciality> speciality = specialtyRepository.findBySpeciality(specialityName);
        if (speciality.isEmpty()) {
            return Optional.empty();
        }


        log.debug("Retrieving speciality with id = {}", speciality.get().getId());
        SpecialityDto specialityDto = specialityMapping.specialityToSpecialityDto(speciality.get());
        return Optional.of(specialityDto);
    }
    @Transactional
    public SpecialityDto updateSpeciality(Long adminId, String speciality, SpecialityDto specialityDto) {

        // TODO: add check for adminId

        log.debug("Starting updating speciality = {}", speciality);

        SpecialityDto specialityDtoFromDb = getSpecialityBySpecialityName(speciality).orElseThrow();

        if (isChanged(specialityDtoFromDb, specialityDto)) {
            Speciality specialityFromDb = specialityMapping.specialityDtoToSpeciality(specialityDtoFromDb);
            specialityMapping.setNewValuesInFields(specialityFromDb, specialityDto);
            Speciality savedSpeciality = specialtyRepository.save(specialityFromDb);
            SpecialityDto savedSpecialityDto = specialityMapping.specialityToSpecialityDto(savedSpeciality);

            log.debug("Updated speciality = {}", speciality);
            return savedSpecialityDto;
        }

        log.debug("Speciality isn't changed for the given name = {}", speciality);
        return specialityDtoFromDb;
    }
    public void deleteSpeciality(Long adminId, String name) {

        // TODO: added check for adminId

        log.debug("Starting deleting speciality = {}", name);
        SpecialityName specialityName = transformSpecialityNameToEnum(name);
        specialtyRepository.deleteBySpeciality(specialityName);
        log.debug("Deleted speciality = {}", name);
    }

    private boolean isChanged(SpecialityDto specialityDtoFromDb, SpecialityDto specialityDto) {
        return !specialityDtoFromDb.getPrice().equals(specialityDto.getPrice()) ||
               !specialityDtoFromDb.getQuantityOfPlaces().equals(specialityDto.getQuantityOfPlaces());
    }
}
