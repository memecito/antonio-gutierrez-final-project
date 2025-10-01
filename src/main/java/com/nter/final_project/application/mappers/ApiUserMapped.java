package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                CountryService.class,
                OrderMapped.class,
                OrderService.class})
public interface ApiUserMapped {

    //INPUT
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", source = "country", qualifiedByName = "getCountryByCode")
    @Mapping(target = "password", conditionQualifiedByName = "isNonEmptyString")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active",ignore = true, defaultValue = "true")
    ApiUser toModel(ApiUserInDto apiUserInDto);

    //OUPUT
    ApiUserOutDto toDto(ApiUser apiUser);

    ApiUserOutDtoMini toDtoMini(ApiUser apiUser);

    /*
    La ide es que atraves del endpoint se sonsiga todas las ordenes de un usuario

    @Mapping(target = "orders", qualifiedByName = "java(getOrders(id)")
    ApiUserOutDtoOrders toDtoOrder(ApiUser apiUser);
     */


    //UPDATE
    ApiUser update(@MappingTarget ApiUser target, ApiUser source);

    @Condition
    @Named("isNonEmptyString")
    default boolean isNonEmptyString(String value) {
        return value != null && !value.isEmpty();
    }



}
