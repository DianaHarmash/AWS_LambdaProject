package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.mappers.SpecialityMapping;
import com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping;
import com.example.diplomaspringproject1_0.repositories.StudentCabinetRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping.buildStudentCabinet;
import static com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping.preBuildStudentCabinet;

@Service
@Slf4j
public class StudentCabinetService {

    private final StudentCabinetRepository studentCabinetRepository;
    private final SpecialityService specialityService;
    private final SpecialityMapping specialityMapping;
    private final StudentCabinetMapping studentCabinetMapping;
    @Autowired
    public StudentCabinetService(StudentCabinetRepository studentCabinetRepository,
                                 SpecialityService specialityService,
                                 SpecialityMapping specialityMapping,
                                 StudentCabinetMapping studentCabinetMapping) {
        this.studentCabinetRepository = studentCabinetRepository;
        this.specialityService = specialityService;
        this.specialityMapping = specialityMapping;
        this.studentCabinetMapping = studentCabinetMapping;
    }

    @Transactional
    public StudentCabinetDto createStudentCabinet(Long adminId, StudentCabinetDto studentCabinetDto) {
        // TODO: check rights of admin
        log.debug("Starting filling student cabinet for student id = {}", studentCabinetDto.getUserId());

        StudentCabinet studentCabinetFromDb = studentCabinetRepository.findById(studentCabinetDto.getId())
                                                                      .orElseThrow();

        SpecialityDto specialityDto = specialityService.getSpecialityBuSpecialityName(studentCabinetDto.getSpeciality());
        Speciality speciality = specialityMapping.specialityDtoToSpeciality(specialityDto);

        studentCabinetFromDb = buildStudentCabinet(studentCabinetFromDb, studentCabinetDto, speciality);
        studentCabinetRepository.save(studentCabinetFromDb);

        log.debug("Filled student cabinet with id = {}", studentCabinetFromDb.getId());

        StudentCabinetDto updatedStudentCabinet = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinetFromDb);
        return updatedStudentCabinet;
    }

    public StudentCabinetDto preCreateStudentCabinetForUser(SystemUser systemUser) {
        log.debug("Starting creating cabinet for student with id = {}", systemUser.getId());

        StudentCabinet preBuildStudentCabinet = preBuildStudentCabinet(systemUser);
        StudentCabinet studentCabinet = studentCabinetRepository.save(preBuildStudentCabinet);

        StudentCabinetDto studentCabinetDto = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinet);
        return studentCabinetDto;
    }

}
