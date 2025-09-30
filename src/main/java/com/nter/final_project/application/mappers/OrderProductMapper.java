package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.OrdersProducts;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
uses = {ProductService.class})
public interface OrderProductMapper {
    //INPUT
    @Mapping(source = "product_id", target = "orderProductId.product", qualifiedByName = "getProductById")
    @Mapping(target = "orderProductId.order", ignore = true)
    OrdersProducts toModel(OrderProductInDto orderProductInDto);
    //OUTPUT
    OrderProductOutDtoMini toDtoMini(OrdersProducts orderProduct);

    OrderProductOutDto toDto(OrdersProducts orderProduct);
}
