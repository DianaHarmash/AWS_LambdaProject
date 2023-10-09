package com.example.diplomaspringproject1_0.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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
        this.quantityOfBillablePlaces = (int) Float.parseFloat(String.valueOf(quantityOfPlaces * 0.4));
    }
}
