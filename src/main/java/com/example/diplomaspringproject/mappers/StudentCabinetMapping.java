package com.example.diplomaspringproject.mappers;

import com.example.diplomaspringproject.dto.StudentCabinetDto;
import com.example.diplomaspringproject.dto.SystemUserDtoForDisplay;
import com.example.diplomaspringproject.entity.Speciality;
import com.example.diplomaspringproject.entity.StudentCabinet;
import com.example.diplomaspringproject.entity.SystemUser;
import org.mapstruct.Mapper;

import static com.example.diplomaspringproject.mappers.SpecialityMapping.transformEnumToSpecialityName;

@Mapper(componentModel = "spring")
public interface StudentCabinetMapping {

    default StudentCabinetDto studentCabinetToStudentCabinetDto(StudentCabinet studentCabinet, SystemUserDtoForDisplay systemUserDto) {
        StudentCabinetDto studentCabinetDto = StudentCabinetDto.builder()
                                                               .id(studentCabinet.getId())
                                                               .year(studentCabinet.getYear())
                                                               .systemUserDto(systemUserDto)
                                                               .speciality(studentCabinet.getSpeciality() != null ?
                                                                           transformEnumToSpecialityName(studentCabinet.getSpeciality().getSpeciality()) :
                                                                           null)
                                                               .group(studentCabinet.getGroupName())
                                                               .build();

        return studentCabinetDto;
    }
    default StudentCabinetDto studentCabinetToStudentCabinetDto(StudentCabinet studentCabinet) {
        SystemUserDtoForDisplay systemUserDto = SystemUserDtoForDisplay.builder()
            .id(studentCabinet.getUser().getId())
            .surname(studentCabinet.getUser().getSurname())
            .name(studentCabinet.getUser().getName())
            .email(studentCabinet.getUser().getEmail())
            .build();

        StudentCabinetDto studentCabinetDto = StudentCabinetDto.builder()
            .id(studentCabinet.getId())
            .year(studentCabinet.getYear())
            .systemUserDto(systemUserDto)
            .speciality(studentCabinet.getSpeciality() != null ?
                transformEnumToSpecialityName(studentCabinet.getSpeciality().getSpeciality()) :
                null)
            .group(studentCabinet.getGroupName())
            .build();

        return studentCabinetDto;
    }

    static StudentCabinet preBuildStudentCabinet(SystemUser systemUserDto) {
        StudentCabinet studentCabinet = new StudentCabinet();

        studentCabinet.setUser(systemUserDto);
        return studentCabinet;
    }

    static StudentCabinet buildStudentCabinet(StudentCabinet studentCabinetFromDb, StudentCabinetDto studentCabinetDtoFromUser, Speciality speciality) {
        studentCabinetFromDb.setYear(studentCabinetDtoFromUser.getYear());
        studentCabinetFromDb.setSpeciality(speciality);
        studentCabinetFromDb.setGroupName(studentCabinetDtoFromUser.getGroup());
        studentCabinetFromDb.setDebtBalance(speciality.getPrice());
        return studentCabinetFromDb;
    }
}
