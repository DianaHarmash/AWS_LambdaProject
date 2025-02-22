package com.example.diplomaspringproject1_0.validators;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import com.example.diplomaspringproject1_0.exceptions.ApiError;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SpecialityValidators implements Validator<SpecialityDto> {

    private final List<String> SPECIALITY_NAMES = Stream.of(SpecialityName.values()).map(SpecialityName::getSpecialityName).toList();

    @Override
    public void validate(SpecialityDto systemUserDto) throws UserException {
        if (SPECIALITY_NAMES.stream().noneMatch(speciality -> speciality.equalsIgnoreCase(systemUserDto.getSpeciality()))) {
            throw new UserException(ApiError.builder()
                                            .message("Speciality mismatches the format.")
                                            .userMessage("Назва не відповідає формату. Має бути одна з тих: " + Arrays.toString(SPECIALITY_NAMES.toArray()) + ".")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }
    }
}
