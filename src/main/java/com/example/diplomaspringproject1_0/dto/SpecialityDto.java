package com.example.diplomaspringproject1_0.dto;

import lombok.*;

import java.math.BigDecimal;

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

    private Integer quantityOfOccupiedPlaces;

    public SpecialityDto(Long id,
                         String speciality,
                         BigDecimal price,
                         Integer quantityOfPlaces,
                         Integer quantityOfOccupiedPlaces) {
        this.id = id;
        this.speciality = speciality;
        this.price = price;
        this.quantityOfPlaces = quantityOfPlaces;
        this.quantityOfOccupiedPlaces = quantityOfOccupiedPlaces;
        this.quantityOfBillablePlaces = (int) Float.parseFloat(String.valueOf(quantityOfPlaces * 0.4));
    }
}
