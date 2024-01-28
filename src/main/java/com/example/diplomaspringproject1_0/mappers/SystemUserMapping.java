package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import org.mapstruct.Named;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Locale;


@Mapper(componentModel = "spring")
public interface SystemUserMapping {


    @Mapping(target = "source.rights", qualifiedByName = "transformStringRightsToEnum")
    SystemUserDto systemUserToSystemUserDto(SystemUser source);

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