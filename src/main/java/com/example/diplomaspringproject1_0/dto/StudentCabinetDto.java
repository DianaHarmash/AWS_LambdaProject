package com.example.diplomaspringproject1_0.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentCabinetDto {

    private Long id;

    private SystemUserDtoForDisplay systemUserDto;

    private String speciality;

    private String group;

    private Integer year;

}
