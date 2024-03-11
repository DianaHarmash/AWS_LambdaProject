package com.example.diplomaspringproject.validators;

import com.example.diplomaspringproject.dto.StudentCabinetDto;
import com.example.diplomaspringproject.entity.enums.SpecialityName;
import com.example.diplomaspringproject.exceptions.ApiError;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.validators.interfaces.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class UserCabinetValidators implements Validator<StudentCabinetDto> {

    Pattern engPattern = Pattern.compile("^[A-Z]{1}[a-z]+-\\d{1,5}$");
    Pattern ukrainianPattern = Pattern.compile("^[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]{1}[абвгґдеєжзиіїйклмнопрстуфхцчшщьюя']+-\\d{1,5}$");

    @Override
    public void validate(StudentCabinetDto systemUserDto) throws UserException {
        String speciality = systemUserDto.getSpeciality();
        String group = systemUserDto.getGroup();
        Integer year = systemUserDto.getYear();

        List<String> specialityNames = Stream.of(SpecialityName.values()).map(SpecialityName::getSpecialityName).toList();

        if (!specialityNames.contains(speciality)) {
            throw new UserException(ApiError.builder()
                    .message("Speciality name mismatches the format.")
                    .userMessage("Спеціальність не відповідає формату.")
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build());
        }

        if (!engPattern.matcher(group).matches() &&
            !ukrainianPattern.matcher(group).matches()) {
            throw new UserException(ApiError.builder()
                                            .message("Group mismatches the format.")
                                            .userMessage("Група не відповідає формату.")
                                            .statusCode(HttpStatus.BAD_REQUEST)
                                            .build());
        }

        if (year < 1 || year > 5) {
            throw new UserException(ApiError.builder()
                    .message("Year mismatches the format.")
                    .userMessage("Рік не відповідає формату.")
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build());
        }

    }
}
