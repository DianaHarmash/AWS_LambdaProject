package com.example.diplomaspringproject1_0.mappers;

import com.example.diplomaspringproject1_0.dto.SpecialityDto;
import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Locale;

@Mapper(componentModel = "spring")
public interface SpecialityMapping {


//    @Mapping(target = "source.speciality", expression = "java(source.speciality.getSpecialityName())")
//    SpecialityDto specialityToSpecialityDto(Speciality source);

    //    @Mapping(target = "source.speciality", qualifiedByName = "transformSpecialityNameToEnum")
//    Speciality specialityDtoToSpeciality(SpecialityDto source);
    default Speciality specialityDtoToSpeciality(SpecialityDto source) {
        SpecialityDto specialityDto = new SpecialityDto(source.getId(),
                                                        source.getSpeciality(),
                                                        source.getPrice(),
                                                        source.getQuantityOfOccupiedPlaces(),
                                                        source.getQuantityOfPlaces());

        Speciality speciality = new Speciality();
        speciality.setId(specialityDto.getId());
        speciality.setSpeciality(transformSpecialityNameToEnum(specialityDto.getSpeciality()));
        speciality.setPrice(specialityDto.getPrice());
        speciality.setQuantityOfPlaces(specialityDto.getQuantityOfPlaces());
        speciality.setQuantityOfOccupiedPlaces(specialityDto.getQuantityOfOccupiedPlaces());
        speciality.setQuantityOfBillablePlaces(specialityDto.getQuantityOfBillablePlaces());

        return speciality;
    }

    default SpecialityDto specialityToSpecialityDto(Speciality source) {
        SpecialityDto specialityDto = SpecialityDto.builder()
                                                   .id(source.getId())
                                                   .speciality(transformEnumToSpecialityName(source.getSpeciality()))
                                                   .quantityOfPlaces(source.getQuantityOfPlaces())
                                                   .price(source.getPrice())
                                                   .quantityOfBillablePlaces(source.getQuantityOfBillablePlaces())
                                                   .build();

        return specialityDto;
    }


    @Named("transformSpecialityNameToEnum")
    static SpecialityName transformSpecialityNameToEnum(String specialityName) {
        specialityName = specialityName.toUpperCase(Locale.ROOT);
        switch (specialityName) {
            case "МЕНЕДЖМЕНТ":             return SpecialityName.MANAGEMENT;
            case "КОМП\'ЮТЕРНІ НАУКИ":     return SpecialityName.COMPUTER_SCIENCE;
            case "ЕКОЛОГІЯ":               return SpecialityName.ECOLOGY;
            case "МАТЕМАТИКА":             return SpecialityName.MATHEMATICS;
            case "КОМП\'ЮТЕРНА ІНЖИНЕРІЯ": return SpecialityName.COMPUTER_ENGINEERING;
            case "СИСТЕМНА АНАЛІТИКА":     return SpecialityName.SYSTEM_ANALYSIS;
            case "ТЕПЛОВА ЕНЕРГЕТИКА":     return SpecialityName.HEAT_ENERGY;
            default:                       return SpecialityName.TEST_NAME;
        }
    }
    @Named("transformEnumToSpecialityName")
    static String transformEnumToSpecialityName(SpecialityName specialityName) {
        switch (specialityName) {
            case MANAGEMENT:           return "Менеджмент";
            case COMPUTER_SCIENCE:     return "Комп\'ютерні науки";
            case ECOLOGY:              return "Екологія";
            case MATHEMATICS:          return "Математика";
            case COMPUTER_ENGINEERING: return "Комп\'ютерна інжинерія";
            case SYSTEM_ANALYSIS:      return "Системна аналітика";
            case HEAT_ENERGY:          return "Теплова енергетика";
            default:                   return "Test";
        }
    }
    default void setNewValuesInFields(Speciality specialityFromDb, SpecialityDto specialityDto) {
        specialityFromDb.setPrice(specialityDto.getPrice());
        specialityFromDb.setQuantityOfPlaces(specialityDto.getQuantityOfPlaces());
        specialityFromDb.setQuantityOfBillablePlaces((int) Float.parseFloat(String.valueOf(specialityFromDb.getQuantityOfBillablePlaces() * 0.4)));
    }
}