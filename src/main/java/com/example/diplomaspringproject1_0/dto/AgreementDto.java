package com.example.diplomaspringproject1_0.dto;

import com.example.diplomaspringproject1_0.entity.Course;
import com.example.diplomaspringproject1_0.entity.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class AgreementDto {

    private Long id;

    private Course course;

    private Student student;

    @JsonProperty("is_paid")
    private Boolean isPaid;

}
