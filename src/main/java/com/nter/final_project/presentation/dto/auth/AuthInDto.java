package com.nter.final_project.presentation.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthInDto {
    @Email(message = "formato no valido")
    @NotBlank(message = "email requerido")
    private String email;

    @NotBlank(message = "contraseña requerida")
    private String password;
}
