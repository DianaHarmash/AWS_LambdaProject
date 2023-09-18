package com.example.diplomaspringproject1_0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentDto {

    private Long id;

    private String surname;

    private String name;

    @JsonProperty("passport_series")
    private Integer passportSeries;

    private Integer year;

    private String group;

    private String speciality;

}
