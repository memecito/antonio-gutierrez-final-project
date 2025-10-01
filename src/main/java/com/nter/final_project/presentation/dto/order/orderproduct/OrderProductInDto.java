package com.nter.final_project.presentation.dto.order.orderproduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class OrderProductInDto {
    @NotNull(message = "id de producto obligatorio")
    private Long id;
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    @Positive(message = "el valor debe ser mayor de 1")
    private int amount;
}
