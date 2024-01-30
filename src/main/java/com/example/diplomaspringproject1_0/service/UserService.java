package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import com.example.diplomaspringproject1_0.exceptions.ApiError;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.SystemUserRepository;
import com.example.diplomaspringproject1_0.security.UserPrincipal;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.diplomaspringproject1_0.security.PasswordEncoder.getPasswordEncoder;

@Service
@Slf4j
public class UserService implements UserDetailsService {

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
    public SystemUserDtoForDisplay createUser(SystemUserDto request) throws UserException {
        validators.getValidators(Entities.USER).validate(request);
        log.debug("Starting creating new user with rights: {}", request.getRights());

        String encrypted = getPasswordEncoder().encode(request.getPassword());
        SystemUser userToSave = systemUserMapping.systemUserDtoToSystemUser(request);
        userToSave.setPassword(encrypted);

        if (systemUserRepository.findSystemUserByRights(userToSave.getRights()).isPresent()) {
            throw new UserException(ApiError.builder()
                .message("Admin is already present in DB.")
                .userMessage("Додаток не передбачає наявність двох адмінів.")
                .statusCode(HttpStatus.CONFLICT)
                .build());
        }

        SystemUser user = systemUserRepository.save(userToSave);

        if (user.getRights().equals(Rights.ROLE_USER)) {
            StudentCabinetDto studentCabinetDto = studentCabinetService.preCreateStudentCabinetForUser(user);
            log.debug("Created new student cabinet with id = {} for user id = {}", studentCabinetDto.getId(), studentCabinetDto.getSystemUserDto().getId());
        }

        SystemUserDtoForDisplay systemUserDto = systemUserMapping.systemUserToSystemUserDtoForDisplay(user);

        log.debug("Created user with id = {}", systemUserDto.getId());

        return systemUserDto;
    }
    public Optional<SystemUserDtoForDisplay> getUserByEmail(String email) {
        log.debug("Getting user by email, where email is {}", email);

        Optional<SystemUser> systemUser = systemUserRepository.findSystemUserByEmail(email);
        if (systemUser.isEmpty()) {
            return Optional.empty();
        }

        SystemUserDtoForDisplay systemUserDto = systemUserMapping.systemUserToSystemUserDtoForDisplay(systemUser.get());

        return Optional.of(systemUserDto);
    }
    @Transactional
    public SystemUserDtoForDisplay updatingUserByEmail(String email, SystemUserDto systemUserDto) {
        log.debug("Starting updating user by email, where email is {}", email);

        SystemUser userFromDb = systemUserRepository.findSystemUserByEmail(email).orElseThrow(
            NoSuchElementException::new);

        if (isChanged(userFromDb, systemUserDto)) {
            systemUserMapping.setNewFields(userFromDb, systemUserDto);
            SystemUser savedUser = systemUserRepository.save(userFromDb);
            SystemUserDtoForDisplay savedUserDto = systemUserMapping.systemUserToSystemUserDtoForDisplay(savedUser);

            log.debug("Finishing updating user by email, where email is {}", email);
            return savedUserDto;
        }

        SystemUserDtoForDisplay unmodifiedUser = systemUserMapping.systemUserToSystemUserDtoForDisplay(userFromDb);
        log.debug("User with email, where email is {}, is not changed.", email);
        return unmodifiedUser;
    }
    @Transactional
    public void deleteUserById(Long id) {
        log.debug("Starting deleting user with id = {}", id);
        systemUserRepository.deleteById(id);
        log.debug("Deleted user with id = {}", id);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SystemUserDto userDto = findByEmail(email).orElseThrow();

        return UserPrincipal.builder()
                .userId(userDto.getId())
                .email(email)
                .password(userDto.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(userDto.getRights())))
                .build();
    }

    public Optional<SystemUserDto> findByEmail(String email) {
        Optional<SystemUser> systemUser = systemUserRepository.findSystemUserByEmail(email);
        return systemUser.map(systemUserMapping::systemUserToSystemUserDto);
    }

    private boolean isChanged(SystemUser userFromDb, SystemUserDto systemUserDto) {
        return !userFromDb.getSurname().equals(systemUserDto.getSurname()) ||
               !userFromDb.getName().equals(systemUserDto.getName()) ||
               !userFromDb.getEmail().equals(systemUserDto.getEmail());
    }
}
