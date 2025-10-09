package com.nter.final_project.presentation.dto.apiuser;

import com.nter.final_project.presentation.dto.country.CountryOutDtoMini;

import java.time.LocalDate;

public record ApiUserOutDto(
        Long id,
        String fullName,
        String email,
        LocalDate createdAt,
        boolean active,
        CountryOutDtoMini country
) {

}
