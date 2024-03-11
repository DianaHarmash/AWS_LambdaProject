package com.example.diplomaspringproject.validators;

import com.example.diplomaspringproject.dto.SystemUserDto;
import com.example.diplomaspringproject.entity.enums.Rights;
import com.example.diplomaspringproject.exceptions.ApiError;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidators implements Validator<SystemUserDto> {

    Pattern engPattern = Pattern.compile("^[A-Z]{1}[a-z]{2,}$");
    Pattern ukrainianPattern = Pattern.compile("^[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]{1}[абвгґдеєжзиіїйклмнопрстуфхцчшщьюя']{2,}$");

    @Override
    public void validate(SystemUserDto systemUserDto) throws UserException {
        String surname = systemUserDto.getSurname();
        String name = systemUserDto.getName();
        String rights = systemUserDto.getRights();

        if (!engPattern.matcher(surname).matches() &&
            !ukrainianPattern.matcher(surname).matches()) {
            throw new UserException(ApiError.builder()
                                            .message("Surname mismatches the format.")
                                            .userMessage("Прізвище не відповідає формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }

        if (!engPattern.matcher(name).matches() &&
            !ukrainianPattern.matcher(name).matches()) {
            throw new UserException(ApiError.builder()
                                            .message("Name mismatches the format.")
                                            .userMessage("Ім\'я не відповідає формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }

        if (!Rights.ROLE_ADMIN.name().equals(rights) &&
            !Rights.ROLE_USER.name().equals(rights)) {
            throw new UserException(ApiError.builder()
                                            .message("Rights mismatch the format.")
                                            .userMessage("Права не відповідають формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }
    }
}
