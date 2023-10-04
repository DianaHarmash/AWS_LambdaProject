package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.StudentCabinetDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentCabinetMapping {
    StudentCabinetDto studentCabinetToStudentCabinetDto(StudentCabinet source);
    StudentCabinet studentCabinetDtoStudentCabinet(StudentCabinetDto source);

    static StudentCabinet preBuildStudentCabinet(SystemUser systemUserDto) {
        StudentCabinet studentCabinet = new StudentCabinet();

        studentCabinet.setUser(systemUserDto);
        return studentCabinet;
    }

    static StudentCabinet buildStudentCabinet(StudentCabinet studentCabinetFromDb, StudentCabinetDto studentCabinetDtoFromUser, Speciality speciality) {
        studentCabinetFromDb.setYear(studentCabinetDtoFromUser.getYear());
        studentCabinetFromDb.setSpeciality(speciality);
        studentCabinetFromDb.setGroupName(studentCabinetFromDb.getGroupName());
        studentCabinetFromDb.setDebtBalance(speciality.getPrice());
        return studentCabinetFromDb;
    }

    static SpecialityName transformStringSpecialityToEnum(String speciality) {
        switch (speciality) {
            case "Менеджмент":             return SpecialityName.MANAGEMENT;
            case "Комп\'ютерні науки":     return SpecialityName.COMPUTER_SCIENCE;
            case "Екологія":               return SpecialityName.ECOLOGY;
            case "Математика":             return SpecialityName.MATHEMATICS;
            case "Комп\'ютерна інжинерія": return SpecialityName.COMPUTER_ENGINEERING;
            case "Системна аналітика":     return SpecialityName.SYSTEM_ANALYSIS;
            case "Теплова енергетика":     return SpecialityName.HEAT_ENERGY;
            default:                       return null;
        }
    }

}
