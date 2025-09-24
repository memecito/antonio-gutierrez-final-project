package com.nter.final_project.presentation.dto.apiuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ApiUserInDto {
    @NotBlank(message = "nombre requerido")
    private String fullName;

    @Email(message = "formato no valido")
    @NotBlank(message = "email requerido")
    private String email;

    @Size(min = 8, message = "tamaño minimo de 8")
    @NotBlank(message = "contraseña requerida")
    private String password;

    private String code_country;
}
