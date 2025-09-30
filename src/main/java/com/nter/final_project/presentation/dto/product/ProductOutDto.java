package com.nter.final_project.presentation.dto.product;

import com.nter.final_project.persistence.entity.OrdersProducts;
import com.nter.final_project.persistence.entity.StatusProduct;

import java.time.LocalDate;
import java.util.Set;

public record ProductOutDto(
        Long id,
        String name,
        Double price,
        StatusProduct status,
        LocalDate createdAt,
        Set<OrdersProducts> orders
) {
}
