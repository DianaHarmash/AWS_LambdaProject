package com.example.diplomaspringproject.mappers;

import com.example.diplomaspringproject.dto.SystemUserDto;
import com.example.diplomaspringproject.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject.entity.SystemUser;
import com.example.diplomaspringproject.entity.enums.Rights;
import org.mapstruct.Named;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Locale;


@Mapper(componentModel = "spring")
public interface SystemUserMapping {


    @Mapping(target = "source.rights", qualifiedByName = "transformStringRightsToEnum")
    SystemUserDto systemUserToSystemUserDto(SystemUser source);

    SystemUserDtoForDisplay systemUserToSystemUserDtoForDisplay(SystemUser source);

    @Mapping(target = "source.rights", qualifiedByName = "transformEnumToStringRights")
    SystemUser systemUserDtoToSystemUser(SystemUserDto source);

    @Named("transformStringRightsToEnum")
    static Rights transformStringRightsToEnum(String rights) {
        switch (rights.toUpperCase(Locale.ROOT)) {
            case "ROLE_USER": return Rights.ROLE_USER;
            case "ROLE_ADMIN": return Rights.ROLE_ADMIN;
            case "FORBIDDEN": return Rights.FORBIDDEN;
            default: return null;
        }
    }
    @Named("transformEnumToStringRights")
    static String transformEnumToStringRights(Rights rights) {
        switch (rights) {
            case ROLE_USER: return "ROLE_USER";
            case ROLE_ADMIN: return "ROLE_ADMIN";
            case FORBIDDEN: return "FORBIDDEN";
            default: return null;
        }
    }
    default void setNewFields(SystemUser userFromDb, SystemUserDto systemUserDto) {
        userFromDb.setSurname(systemUserDto.getSurname());
        userFromDb.setName(systemUserDto.getName());
        userFromDb.setEmail(systemUserDto.getEmail());
    }

}