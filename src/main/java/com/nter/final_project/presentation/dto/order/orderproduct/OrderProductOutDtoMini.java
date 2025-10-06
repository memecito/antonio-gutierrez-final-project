package com.nter.final_project.presentation.dto.order.orderproduct;

import com.nter.final_project.presentation.dto.product.ProductOutDtoMIni;

public record OrderProductOutDtoMini(
        ProductOutDtoMIni product,
        Integer amount
) {
}
