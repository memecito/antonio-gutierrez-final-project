package com.nter.final_project.presentation.dto.order.orderproduct;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.Product;

public record OrderProductOutDto(
        Order order,
        Product product,
        Integer amount
) {
}
