package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import com.nter.final_project.presentation.dto.apiuser.ApiUserUpdateDto;
import com.nter.final_project.presentation.dto.auth.AuthInDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                CountryService.class,
                OrderService.class})
public interface ApiUserMapped {

    //INPUT
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", source = "country", qualifiedByName = "getCountryByCode")
    @Mapping(target = "password", conditionQualifiedByName = "isNonEmptyString")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", ignore = true, defaultValue = "true")
    ApiUser toModel(ApiUserInDto apiUserInDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", source = "country", qualifiedByName = "getCountryByCode")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", ignore = true, defaultValue = "true")
    ApiUser toModelUpdate(ApiUserUpdateDto apiUserUpdateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "country", ignore = true)
    ApiUser toModelAuth(AuthInDto authInDto);


    //OUPUT
    ApiUserOutDto toDto(ApiUser apiUser);

    ApiUserOutDtoMini toDtoMini(ApiUser apiUser);

    //UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country", source = "country", qualifiedByName = "mapCountry")
    ApiUser update(@MappingTarget ApiUser target, ApiUser source);

    @Condition
    @Named("isNonEmptyString")
    default boolean isNonEmptyString(String value) {
        return value != null && !value.isEmpty();
    }

    @Named("mapCountry")
    default Country mapCountry(Country country) {
        return country;
    }


}
