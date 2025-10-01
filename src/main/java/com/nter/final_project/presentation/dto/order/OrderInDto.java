package com.nter.final_project.presentation.dto.order;

import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public final class OrderInDto {

    @NotNull(message = "Usuario obligatorio")
    private Long user;
    @NotNull(message = "Productos obligatorios")
    private Set<OrderProductInDto> products;
}
