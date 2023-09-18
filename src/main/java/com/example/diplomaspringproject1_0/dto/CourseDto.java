package com.example.diplomaspringproject1_0.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseDto {

    private Long id;

    private String name;

    private List<Integer> years;

    private BigDecimal price;
}
