package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.dto.SystemUserDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static com.example.diplomaspringproject1_0.mappers.SpecialityMapping.transformEnumToSpecialityName;

@Mapper(componentModel = "spring")
public interface StudentCabinetMapping {

    default StudentCabinetDto studentCabinetToStudentCabinetDto(StudentCabinet studentCabinet, SystemUserDto systemUserDto) {
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
