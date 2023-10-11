package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import com.example.diplomaspringproject1_0.exceptions.ApiError;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.SystemUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            log.debug("Created new student cabinet with id = {} for user id = {}", studentCabinetDto.getId(), studentCabinetDto.getSystemUserDto().getId());
        }

        SystemUserDto systemUserDto = systemUserMapping.systemUserToSystemUserDto(user);
        log.debug("Created user with id = {}", systemUserDto.getId());

        return systemUserDto;
    }
    public Optional<SystemUserDto> getUserById(Long id) {
        log.debug("Getting user by id, and id = {}", id);

        Optional<SystemUser> systemUser = systemUserRepository.findById(id);
        if (!systemUser.isPresent()) {
            return Optional.empty();
        }

        SystemUserDto systemUserDto = systemUserMapping.systemUserToSystemUserDto(systemUser.get());

        return Optional.of(systemUserDto);
    }
    @Transactional
    public SystemUserDto updatingUserById(Long id, SystemUserDto systemUserDto) {
        log.debug("Starting updating user with id = {}", id);

        SystemUserDto userFromDb = getUserById(id).orElseThrow();

        if (isChanged(userFromDb, systemUserDto)) {
            SystemUser systemUserFromBb = systemUserMapping.systemUserDtoToSystemUser(userFromDb);
            systemUserMapping.setNewNames(systemUserFromBb, systemUserDto);
            SystemUser savedUser = systemUserRepository.save(systemUserFromBb);
            SystemUserDto savedUserDto = systemUserMapping.systemUserToSystemUserDto(savedUser);

            log.debug("Updated user with id = {}", id);
            return savedUserDto;
        }

        log.debug("User isn't changed for the given id = {}", id);
        return userFromDb;
    }
    public void deleteUserById(Long id) {
        log.debug("Starting deleting user with id = {}", id);
        systemUserRepository.deleteById(id);
        log.debug("Deleted user with id = {}", id);
    }

    private boolean isChanged(SystemUserDto userFromDb, SystemUserDto systemUserDto) {
        return !userFromDb.getSurname().equals(systemUserDto.getSurname()) ||
               !userFromDb.getName().equals(systemUserDto.getName());
    }
}
