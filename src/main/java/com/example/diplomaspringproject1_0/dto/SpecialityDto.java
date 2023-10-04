package com.example.diplomaspringproject1_0.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecialityDto {

    private Long id;

    private String speciality;

    private BigDecimal price;

    private Integer quantityOfPlaces;

    private Integer quantityOfBillablePlaces;

    public SpecialityDto(String speciality,
                         BigDecimal price,
                         Integer quantityOfPlaces) {
        this.speciality = speciality;
        this.price = price;
        this.quantityOfPlaces = quantityOfPlaces;
        this.quantityOfBillablePlaces = Integer.parseInt(String.valueOf(quantityOfPlaces * 0.4));
    }
}
