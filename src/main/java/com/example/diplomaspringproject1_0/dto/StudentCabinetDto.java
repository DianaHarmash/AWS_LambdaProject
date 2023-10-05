package com.example.diplomaspringproject1_0.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentCabinetDto {

    private Long id;

    private SystemUserDto systemUserDto;

    private Integer year;

    private String group;

    private String speciality;

    private Long userId;

}
