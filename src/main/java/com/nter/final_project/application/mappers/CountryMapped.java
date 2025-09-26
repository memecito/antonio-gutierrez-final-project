package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface CountryMapped {

    //UPDATE
    Country update(@MappingTarget Country target, Country source);
}
