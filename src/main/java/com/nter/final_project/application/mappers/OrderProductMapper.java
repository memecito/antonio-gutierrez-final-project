package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
    //INPUT
    OrderProduct toModel(OrderProductInDto orderProductInDto);
    //OUTPUT
    @Mapping(target = "product", source = "product.id")
    OrderProductOutDtoMini toDtoMini(OrderProduct orderProduct);

    OrderProductOutDto toDto(OrderProduct orderProduct);
}
