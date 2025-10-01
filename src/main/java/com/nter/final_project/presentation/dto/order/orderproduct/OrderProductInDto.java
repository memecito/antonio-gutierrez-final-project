package com.nter.final_project.presentation.dto.order.orderproduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class OrderProductInDto {
    @NotBlank(message = "id de producto obligatorio")
    private Long id;
    @Min(value = 1, message = "cantidad minima obligatoria")
    private int amount;
}
