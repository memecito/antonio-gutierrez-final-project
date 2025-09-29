package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses ={Country.class})
public interface ApiUserMapped {

    //INPUT
    @Mapping(target= "id", ignore= true)
    @Mapping(target="country", ignore= true)
    ApiUser toModel(ApiUserInDto apiUserInDto);

    //OUPUT
    ApiUserOutDto toDto(ApiUser apiUser);
    ApiUserOutDtoMini toDtoMini(ApiUser apiUser);

    //UPDATE
    ApiUser update(@MappingTarget ApiUser target, ApiUser source);
}
