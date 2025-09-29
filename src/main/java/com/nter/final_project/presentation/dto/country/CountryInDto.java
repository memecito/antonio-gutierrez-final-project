package com.nter.final_project.presentation.dto.country;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CountryInDto {
    @NotBlank(message = "codigo obligatorio")
    private String code;
    @NotBlank(message = "nombre obligatorio")
    private String name;
}
