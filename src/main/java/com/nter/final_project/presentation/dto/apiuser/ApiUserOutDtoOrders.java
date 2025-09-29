package com.nter.final_project.presentation.dto.apiuser;

import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;

import java.util.Set;

public record ApiUserOutDtoOrders(
        ApiUserOutDto user,
        Set<OrderOutDtoMIni> orders
) {
}
