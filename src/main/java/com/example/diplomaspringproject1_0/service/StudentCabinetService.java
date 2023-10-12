package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.CodeOfOperation;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.mappers.SpecialityMapping;
import com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.StudentCabinetRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping.buildStudentCabinet;
import static com.example.diplomaspringproject1_0.mappers.StudentCabinetMapping.preBuildStudentCabinet;

@Service
@Slf4j
public class StudentCabinetService {
    private final String PAYMENT_IS_SUCCESSFUL = "Оплата пройшла успішно";
    private final String PAYMENT_IS_FAILED = "При оплаті за навчання від студента сталася помилка";

    private final StudentCabinetRepository studentCabinetRepository;
    private final SpecialityService specialityService;
    private final SystemUserMapping systemUserMapping;
    private final SpecialityMapping specialityMapping;
    private final StudentCabinetMapping studentCabinetMapping;
    private final BalanceService balanceService;
    @Autowired
    public StudentCabinetService(StudentCabinetRepository studentCabinetRepository,
                                 SpecialityService specialityService,
                                 SystemUserMapping systemUserMapping,
                                 SpecialityMapping specialityMapping,
                                 StudentCabinetMapping studentCabinetMapping,
                                 BalanceService balanceService) {
        this.studentCabinetRepository = studentCabinetRepository;
        this.specialityService = specialityService;
        this.systemUserMapping = systemUserMapping;
        this.specialityMapping = specialityMapping;
        this.studentCabinetMapping = studentCabinetMapping;
        this.balanceService = balanceService;
    }

    @Transactional
    public StudentCabinetDto updateStudentCabinet(Long adminId, StudentCabinetDto studentCabinetDto) {
        // TODO: check rights of admin
        log.debug("Starting filling student cabinet for student id = {}", studentCabinetDto.getSystemUserDto().getId());

        StudentCabinet studentCabinetFromDb = studentCabinetRepository.findByUserId(studentCabinetDto.getSystemUserDto().getId())
                                                                      .orElseThrow();

        SpecialityDto specialityDto = specialityService.getSpecialityBySpecialityName(studentCabinetDto.getSpeciality())
                                                       .orElseThrow();
        Speciality speciality = specialityMapping.specialityDtoToSpeciality(specialityDto);
        studentCabinetFromDb = buildStudentCabinet(studentCabinetFromDb, studentCabinetDto, speciality);
        studentCabinetRepository.save(studentCabinetFromDb);

        log.debug("Filled student cabinet with id = {}", studentCabinetFromDb.getId());

        SystemUserDto systemUserDto = systemUserMapping.systemUserToSystemUserDto(studentCabinetFromDb.getUser());
        StudentCabinetDto updatedStudentCabinet = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinetFromDb, systemUserDto);
        return updatedStudentCabinet;
    }
    public StudentCabinetDto preCreateStudentCabinetForUser(SystemUser systemUser) {
        log.debug("Starting creating cabinet for student with id = {}", systemUser.getId());

        StudentCabinet preBuildStudentCabinet = preBuildStudentCabinet(systemUser);
        StudentCabinet studentCabinet = studentCabinetRepository.save(preBuildStudentCabinet);
        SystemUserDto systemUserDto = systemUserMapping.systemUserToSystemUserDto(systemUser);
        StudentCabinetDto studentCabinetDto = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinet, systemUserDto);

        return studentCabinetDto;
    }
    @Transactional
    public ResponseEntity<String> transitPayToTheBalance(Long studentId) {
        log.debug("Starting transferring payment from student with id = {}", studentId);

        StudentCabinet studentCabinetFromDb = studentCabinetRepository.findByUserId(studentId)
                                                                      .orElseThrow();

        BalanceDto balanceDto = buildPaymentFromStudent(studentCabinetFromDb.getDebtBalance());
        try {
            BalanceDto transferredFunds = balanceService.manageBalance(studentCabinetFromDb.getId(), balanceDto);
            log.debug("Transferring completed for student with id = {}", studentId);
        } catch (UserException e) {
            return new ResponseEntity<>(PAYMENT_IS_FAILED,
                                        HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(PAYMENT_IS_SUCCESSFUL,
                                    HttpStatus.OK);
    }
    public Optional<StudentCabinetDto> getStudentCabinet(Long id, String surname, String name) {
        Optional<StudentCabinet> studentCabinet = Optional.empty();

        if (id != null) {
            log.debug("Getting student cabinet by id, and id = {}", id);
            studentCabinet = studentCabinetRepository.findById(id);
        } else if (surname != null && name != null) {
            log.debug("Getting student cabinet by surname = {}, name = {}", surname, name);
            studentCabinet = studentCabinetRepository.findBySurnameAndName(surname, name);
        }

        if (studentCabinet.isEmpty()) {
            return Optional.empty();
        }

        StudentCabinetDto studentCabinetDto = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinet.get());

        return Optional.of(studentCabinetDto);
    }
    public void deleteStudentCabinet(Long adminId, Long id) {
        // TODO: add validation for adminId

        log.debug("Staring deleting student cabinet with id = {}", id);
        studentCabinetRepository.deleteById(id);
        log.debug("Deleted student cabinet with id = {}", id);
    }

    private BalanceDto buildPaymentFromStudent(BigDecimal money) {

        BalanceDto balanceDto = BalanceDto.builder()
                .sum(money)
                .codeOfOperation(CodeOfOperation.INCOME_FROM_STUDENTS)
                .date(Date.valueOf(LocalDate.now()))
                .build();

        return balanceDto;
    }

}
