package com.nter.final_project.presentation.dto.product;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ProductUpdateDto {
    private String name;
    @Min(value = 1, message = "precio minimo requerido")
    private Double price;
}
