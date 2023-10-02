package com.example.diplomaspringproject1_0.validators;

import com.example.diplomaspringproject1_0.dto.SystemUsersDto;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import com.example.diplomaspringproject1_0.exceptions.ApiError;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidators implements Validator<SystemUsersDto> {

    Pattern engPattern = Pattern.compile("^[A-Z]{1}[a-z]{2,}$");
    Pattern ukrainianPattern = Pattern.compile("^[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]{1}[абвгґдеєжзиіїйклмнопрстуфхцчшщьюя]{2,}$");

    @Override
    public void validate(SystemUsersDto systemUsersDto) throws UserException {
        String surname = systemUsersDto.getSurname();
        String name = systemUsersDto.getName();
        String rights = systemUsersDto.getRights();

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
                                            .message("Surname mismatches the format.")
                                            .userMessage("Ім\'я не відповідає формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }

        if (!Rights.ADMIN.name().equalsIgnoreCase(rights) &&
            !Rights.STUDENT.name().equalsIgnoreCase(rights)) {
            throw new UserException(ApiError.builder()
                                            .message("Surname mismatches the format.")
                                            .userMessage("Права не відповідають формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }
    }
}
