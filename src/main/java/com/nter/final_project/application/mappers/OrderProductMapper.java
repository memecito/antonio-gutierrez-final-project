package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ProductService.class})
public interface OrderProductMapper {
    //INPUT
    @Mapping(target = "product", source = "id", qualifiedByName = "getProductById")
    OrderProduct toModel(OrderProductInDto orderProductInDto);

    //OUTPUT
    @Mapping(target = "id", source = "product.id")
    OrderProductOutDtoMini toDtoMini(OrderProduct orderProduct);

    OrderProductOutDto toDto(OrderProduct orderProduct);
}
