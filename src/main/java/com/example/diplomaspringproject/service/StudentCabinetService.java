package com.example.diplomaspringproject.service;

import com.example.diplomaspringproject.dto.BalanceDto;
import com.example.diplomaspringproject.dto.SpecialityDto;
import com.example.diplomaspringproject.dto.StudentCabinetDto;
import com.example.diplomaspringproject.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject.entity.Speciality;
import com.example.diplomaspringproject.entity.StudentCabinet;
import com.example.diplomaspringproject.entity.SystemUser;
import com.example.diplomaspringproject.entity.enums.CodeOfOperation;
import com.example.diplomaspringproject.entity.enums.Entities;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.facades.Validators;
import com.example.diplomaspringproject.mappers.SpecialityMapping;
import com.example.diplomaspringproject.mappers.StudentCabinetMapping;
import com.example.diplomaspringproject.mappers.SystemUserMapping;
import com.example.diplomaspringproject.repositories.StudentCabinetRepository;
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

import static com.example.diplomaspringproject.mappers.StudentCabinetMapping.buildStudentCabinet;
import static com.example.diplomaspringproject.mappers.StudentCabinetMapping.preBuildStudentCabinet;

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
    private final Validators validators;
    private final BalanceService balanceService;
    @Autowired
    public StudentCabinetService(StudentCabinetRepository studentCabinetRepository,
                                 SpecialityService specialityService,
                                 SystemUserMapping systemUserMapping,
                                 SpecialityMapping specialityMapping,
                                 StudentCabinetMapping studentCabinetMapping,
                                 BalanceService balanceService,
                                 Validators validators) {
        this.studentCabinetRepository = studentCabinetRepository;
        this.specialityService = specialityService;
        this.systemUserMapping = systemUserMapping;
        this.specialityMapping = specialityMapping;
        this.studentCabinetMapping = studentCabinetMapping;
        this.balanceService = balanceService;
        this.validators = validators;
    }

    @Transactional
    public StudentCabinetDto updateStudentCabinet(StudentCabinetDto studentCabinetDto) throws UserException {
        log.debug("Starting filling student cabinet for student id = {}", studentCabinetDto.getSystemUserDto().getId());

        validators.getValidators(Entities.STUDENT_CABINET).validate(studentCabinetDto);

        StudentCabinet studentCabinetFromDb = studentCabinetRepository.findByUserId(studentCabinetDto.getSystemUserDto().getId())
                                                                      .orElseThrow();

        try {
            SpecialityDto specialityDto = specialityService.getSpecialityBySpecialityName(studentCabinetDto.getSpeciality())
                    .orElseThrow();
            Speciality speciality = specialityMapping.specialityDtoToSpeciality(specialityDto);
            studentCabinetFromDb = buildStudentCabinet(studentCabinetFromDb, studentCabinetDto, speciality);
            studentCabinetRepository.save(studentCabinetFromDb);
            specialityService.addStudentToSpeciality(specialityDto);
        } catch (UserException ex) {
            log.debug("Student Cabinet failed with ex: \'{}\'", ex.getMessage());
            throw ex;
        }

        log.debug("Filled student cabinet with id = {}", studentCabinetFromDb.getId());

        SystemUserDtoForDisplay systemUserDto = systemUserMapping.systemUserToSystemUserDtoForDisplay(studentCabinetFromDb.getUser());
        StudentCabinetDto updatedStudentCabinet = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinetFromDb, systemUserDto);
        return updatedStudentCabinet;
    }
    public StudentCabinetDto preCreateStudentCabinetForUser(SystemUser systemUser) {
        log.debug("Starting creating cabinet for student with id = {}", systemUser.getId());

        StudentCabinet preBuildStudentCabinet = preBuildStudentCabinet(systemUser);
        StudentCabinet studentCabinet = studentCabinetRepository.save(preBuildStudentCabinet);
        SystemUserDtoForDisplay systemUserDto = systemUserMapping.systemUserToSystemUserDtoForDisplay(systemUser);
        StudentCabinetDto studentCabinetDto = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinet, systemUserDto);

        return studentCabinetDto;
    }
    @Transactional
    public ResponseEntity<String> transitPayToTheBalance(String email) {
        log.debug("Starting transferring payment from {}}", email);

        StudentCabinet studentCabinetFromDb = studentCabinetRepository.findByEmail(email)
                                                                      .orElseThrow();

        BalanceDto balanceDto = buildPaymentFromStudent(studentCabinetFromDb.getDebtBalance());
        try {
            balanceService.manageBalance(balanceDto);
            log.debug("Transferring completed for student = {}", email);
        } catch (UserException e) {
            return new ResponseEntity<>(PAYMENT_IS_FAILED,
                                        HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(PAYMENT_IS_SUCCESSFUL,
                                    HttpStatus.OK);
    }
    public Optional<StudentCabinetDto> getStudentCabinet(String email) {
        log.debug("Getting student cabinet by emal, and email = {}", email);
        Optional<StudentCabinet> studentCabinet = studentCabinetRepository.findByEmail(email);

        if (studentCabinet.isEmpty()) {
            return Optional.empty();
        }

        StudentCabinetDto studentCabinetDto = studentCabinetMapping.studentCabinetToStudentCabinetDto(studentCabinet.get());

        return Optional.of(studentCabinetDto);
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
