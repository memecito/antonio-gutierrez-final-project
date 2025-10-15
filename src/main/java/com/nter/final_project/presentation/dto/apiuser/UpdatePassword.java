package com.nter.final_project.presentation.dto.apiuser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UpdatePassword {
    @Size(min = 8, message = "tamaño minimo de 8")
    @NotBlank(message = "contraseña requerida")
    private String password;
}
