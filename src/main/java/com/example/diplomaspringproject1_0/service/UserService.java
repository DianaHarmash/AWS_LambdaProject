package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.auth.AuthService;
import com.example.diplomaspringproject1_0.auth.LoginResponse;
import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.SystemUserRepository;
import com.example.diplomaspringproject1_0.security.JwtConfiguration;
import com.example.diplomaspringproject1_0.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (user.getRights().equals(Rights.ROLE_USER)) {
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
    @Transactional
    public void deleteUserById(Long adminId, Long id) {
        checkAdminRights(adminId);

        log.debug("Starting deleting user with id = {}", id);
        systemUserRepository.deleteById(id);
        log.debug("Deleted user with id = {}", id);
    }
    public boolean checkAdminRights(Long userId) {
        SystemUserDto systemUserDto = getUserById(userId).orElseThrow();
        return systemUserDto.getRights().equals("ADMIN");
    }

    public Optional<SystemUserDto> findByEmail(String email) {
        Optional<SystemUser> systemUser = systemUserRepository.findSystemUserByEmail(email);
        return systemUser.map(systemUserMapping::systemUserToSystemUserDto);
    }

    private boolean isChanged(SystemUserDto userFromDb, SystemUserDto systemUserDto) {
        return !userFromDb.getSurname().equals(systemUserDto.getSurname()) ||
               !userFromDb.getName().equals(systemUserDto.getName());
    }
}
