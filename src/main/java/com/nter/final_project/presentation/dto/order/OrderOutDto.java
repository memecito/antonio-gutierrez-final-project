package com.nter.final_project.presentation.dto.order;

import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;

import java.time.LocalDate;
import java.util.Set;

public record OrderOutDto(
        Long id,
        StatusOrder status,
        LocalDate createdAt,
        ApiUserOutDtoMini user,
        Set<OrderProductOutDtoMini> products

        //todo falta establecer como se ven los productos
) {
}
