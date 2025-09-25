package com.nter.final_project.presentation.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public final class OrderInDto {

    private Long user;
    private Set<OrderProductInDto> products;
}
