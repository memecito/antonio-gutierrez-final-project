package com.nter.final_project.presentation.dto.order.orderproduct;

import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;

public record OrderProductOutDtoMini(
        Integer amount,
        OrderOutDtoMIni order
) {
}
