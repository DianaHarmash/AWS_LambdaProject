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
            case "STUDENT": return Rights.STUDENT;
            case "ADMIN": return Rights.ADMIN;
            case "FORBIDDEN": return Rights.FORBIDDEN;
            default: return null;
        }
    }
    @Named("transformEnumToStringRights")
    static String transformEnumToStringRights(Rights rights) {
        switch (rights) {
            case STUDENT: return "STUDENT";
            case ADMIN: return "ADMIN";
            case FORBIDDEN: return "FORBIDDEN";
            default: return null;
        }
    }
    default void setNewNames(SystemUser userFromDb, SystemUserDto systemUserDto) {
        userFromDb.setSurname(systemUserDto.getSurname());
        userFromDb.setName(userFromDb.getName());
    }

}