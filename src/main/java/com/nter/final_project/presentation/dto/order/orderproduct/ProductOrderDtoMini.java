package com.nter.final_project.presentation.dto.order.orderproduct;

import com.nter.final_project.persistence.entity.Order;

public record ProductOrderDtoMini(

        Order order,
        int amount
) {
}
