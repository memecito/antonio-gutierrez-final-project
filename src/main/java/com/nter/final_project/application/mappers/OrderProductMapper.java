package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductInDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDto;
import com.nter.final_project.presentation.dto.order.orderproduct.OrderProductOutDtoMini;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
    //INPUT
    OrderProduct toModel(OrderProductInDto orderProductInDto);
    //OUTPUT
    OrderProductOutDtoMini toDtoMini(OrderProduct orderProduct);
    OrderProductOutDto toDto(OrderProduct orderProduct);
}
