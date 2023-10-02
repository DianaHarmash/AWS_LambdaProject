package com.example.diplomaspringproject1_0.dto;

import com.example.diplomaspringproject1_0.entity.enums.Speciality;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecialityPriceDto {

    private Long id;

    private Speciality speciality;

    private BigDecimal price;
}
