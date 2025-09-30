package com.nter.final_project.presentation.dto.product;

import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.StatusProduct;
import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import com.nter.final_project.presentation.dto.order.orderproduct.ProductOrderDtoMini;

import java.time.LocalDate;
import java.util.Set;

public record ProductOutDto(
        Long id,
        String name,
        Double price,
        StatusProduct status,
        LocalDate created
) {
}
