package com.nter.final_project.presentation.dto.apiuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ApiUserUpdateDto {
    private String fullName;

    @Email(message = "formato no valido")
    private String email;

    @Size(min = 8, message = "tamaño minimo de 8")
    private String password;

    @Size(min = 2, max = 2, message = "tamaño del codigo erroneo")
    private String country;
}
