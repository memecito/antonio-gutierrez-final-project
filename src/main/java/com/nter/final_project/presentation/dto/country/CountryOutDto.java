package com.nter.final_project.presentation.dto.country;

import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;

import java.util.Set;

public record CountryOutDto(
        String code,
        String name,
        Set<ApiUserOutDto> users
) {
}
