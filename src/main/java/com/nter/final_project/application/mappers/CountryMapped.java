package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.Country;
import org.mapstruct.MappingTarget;

public interface CountryMapped {

    //UPDATE
    Country update(@MappingTarget Country target, Country source);
}
