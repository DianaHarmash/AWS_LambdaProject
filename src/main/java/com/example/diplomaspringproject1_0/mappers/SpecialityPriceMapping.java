package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.SpecialityPriceDto;
import com.example.diplomaspringproject1_0.entity.SpecialityPrice;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface SpecialityPriceMapping {

    SpecialityPriceDto specialityPriceToSpecialityPriceDto(SpecialityPrice source);
    SpecialityPrice specialityPriceDtoToSpecialityPrice(SpecialityPriceDto source);

}