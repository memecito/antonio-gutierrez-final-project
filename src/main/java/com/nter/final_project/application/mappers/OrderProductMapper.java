package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {ProductService.class})
public interface OrderProductMapper {
    //INPUT
    @Mapping(target = "orderProductId.product", source = "id", qualifiedByName = "getProductById")
    @Mapping(target = "orderProductId.order", ignore = true)
    OrderProduct toModel(OrderProductInDto orderProductInDto);

    //OUTPUT
    @Mapping(target = "product", source = "orderProductId.product")
    OrderProductOutDtoMini toDtoMini(OrderProduct orderProduct);

    @Mapping(source = "orderProductId.order", target = "order")
    @Mapping(source = "orderProductId.product", target = "product")
    OrderProductOutDto toDto(OrderProduct orderProduct);

    //METODOS
    @Mapping(target = "orderProductId", ignore = true)
    OrderProduct update(@MappingTarget OrderProduct target, OrderProduct source);
}
