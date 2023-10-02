package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.StudentDto;
import com.example.diplomaspringproject1_0.entity.Student;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface StudentMapping {

    StudentDto studentToStudentDto(Student source);
    Student studentDtoStudent(StudentDto source);

}
