package com.nter.final_project.presentation.dto.product;

import com.nter.final_project.persistence.entity.StatusProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public final class ProductInDto {
    @NotBlank(message = "nombre obligatorio")
    private String name;
    @NotNull(message = "precio obligatorio")
    @Range(min = 1, message = "precio minimo superio a 1")
    private Double price;
    private StatusProduct status;
}
