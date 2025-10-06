package com.nter.final_project.presentation.dto.country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CountryUpdateDto {

    @NotBlank(message = "codigo obligatorio")
    @Size(min = 2, max = 2, message = "tamaño del codigo erroneo")
    private String code;
}
