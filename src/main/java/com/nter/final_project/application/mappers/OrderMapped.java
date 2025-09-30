package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.application.services.impl.ApiUserServiceImpl;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.presentation.dto.order.OrderInDto;
import com.nter.final_project.presentation.dto.order.OrderOutDto;
import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderProductMapper.class})
public interface OrderMapped {
//todo tienes que mapear la entradas de ordenes para que te den el usuario,
// que creo que ya esta, y te queda que busque los productos y los meta en la tabla de order
// product... Aunque otra cosas seria crear un mapper para la tabal OrderProduct
// y desde alli mandar a las demas

    //INPUT
    @Mapping(target = "user.id", source = "user")
    @Mapping(target = "ordersProducts.orderProductId.product.id", source = "products.product_id" )
    Order toModel(OrderInDto orderInDto);

    //OUPUT
    @Mapping(target = "user", source = "user")
    @Mapping(target = "products", source = "ordersProducts")
    OrderOutDto toDto(Order order);

    OrderOutDtoMIni toDtoMini(Order order);

    //UPDATE
    Order update(@MappingTarget Order targer, Order source);
}
