package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import com.nter.final_project.presentation.dto.country.CountryOutDto;
import com.nter.final_project.presentation.dto.country.CountryOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CountryMapped {

    //INPUT
    Country toModel(CountryInDto country);

    //OUPUT
    @Mapping(target = "users", source = "apiUsers")
    CountryOutDto toDto(Country country);

    CountryOutDtoMini toDtoMini(Country country);

    //UPDATE
    Country update(@MappingTarget Country target, Country source);
}
