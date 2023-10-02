package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.SystemUsersDto;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.SystemUserMapping;
import com.example.diplomaspringproject1_0.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SystemUserMapping systemUserMapping;

    private final Validators validators;

    @Autowired
    public UserService(UserRepository userRepository,
                       SystemUserMapping systemUserMapping,
                       Validators validators) {
        this.userRepository = userRepository;
        this.systemUserMapping = systemUserMapping;
        this.validators = validators;
    }


    public SystemUsersDto createUser(SystemUsersDto request) throws UserException {
        validators.getValidators(Entities.USER).validate(request);

        SystemUser userToSave = systemUserMapping.systemUserDtoToSystemUser(request);
        SystemUser user = userRepository.save(userToSave);
        SystemUsersDto systemUsersDto = systemUserMapping.systemUserToSystemUserDto(user);

        return systemUsersDto;
    }
}
