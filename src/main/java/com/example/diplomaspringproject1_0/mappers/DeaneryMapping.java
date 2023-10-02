package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.DeaneryDto;
import com.example.diplomaspringproject1_0.entity.Deanery;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface DeaneryMapping {

    DeaneryDto deaneryToDeaneryDto(Deanery source);
    Deanery deaneryDtoToDeanery(DeaneryDto source);

}