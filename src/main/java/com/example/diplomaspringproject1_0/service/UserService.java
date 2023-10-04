package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.SystemUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final SystemUserRepository systemUserRepository;
    private final SystemUserMapping systemUserMapping;
    private final StudentCabinetService studentCabinetService;
    private final Validators validators;
    @Autowired
    public UserService(SystemUserRepository systemUserRepository,
                       SystemUserMapping systemUserMapping,
                       StudentCabinetService studentCabinetService,
                       Validators validators) {
        this.systemUserRepository = systemUserRepository;
        this.systemUserMapping = systemUserMapping;
        this.studentCabinetService = studentCabinetService;
        this.validators = validators;
    }

    @Transactional
    public SystemUserDto createUser(SystemUserDto request) throws UserException {
        validators.getValidators(Entities.USER).validate(request);
        log.debug("Starting creating new user with rights: {}", request.getRights());

        SystemUser userToSave = systemUserMapping.systemUserDtoToSystemUser(request);
        SystemUser user = systemUserRepository.save(userToSave);
        if (user.getRights().equals(Rights.STUDENT)) {
            StudentCabinetDto studentCabinetDto = studentCabinetService.preCreateStudentCabinetForUser(user);
            log.debug("Created new student cabinet with id = {} for user id = {}", studentCabinetDto.getId(), studentCabinetDto.getUserId());
        }

        SystemUserDto systemUserDto = systemUserMapping.systemUserToSystemUserDto(user);
        log.debug("Created user with id = {}", systemUserDto.getId());

        return systemUserDto;
    }
}
