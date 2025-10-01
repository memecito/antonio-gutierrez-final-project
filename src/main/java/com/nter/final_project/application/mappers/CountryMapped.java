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
    @Mapping(target = "apiUsers", ignore = true)
    Country toModel(CountryInDto country);

    //OUPUT
    @Mapping(target = "users", source = "apiUsers")
    CountryOutDto toDto(Country country);

    CountryOutDtoMini toDtoMini(Country country);

    //UPDATE
    @Mapping(target = "code", ignore = true)

    Country update(@MappingTarget Country target, Country source);
}
