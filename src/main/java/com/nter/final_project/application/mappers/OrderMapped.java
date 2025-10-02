package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.presentation.dto.order.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                ApiUserService.class,
                OrderProductMapper.class})
public interface OrderMapped {
//todo tienes que mapear la entradas de ordenes para que te den el usuario,
// que creo que ya esta, y te queda que busque los productos y los meta en la tabla de order
// product... Aunque otra cosas seria crear un mapper para la tabal OrderProduct
// y desde alli mandar a las demas

    //INPUT
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user", qualifiedByName = "getUserById")
    @Mapping(target = "orderProducts", source = "products")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status",ignore = true, defaultValue = "processing")
    Order toModel(OrderInDto orderInDto);

    @Mapping(target = "orderProducts", source = "products")
    Order toModelUpdate(OrderUpdateDto orderUpdateDto);

    default String toModelStatus( OrderStatusInDto statusInDto){
        if (statusInDto == null) {
            return null;
        }
        return statusInDto.getStatus();
    }


    //OUPUT
    //@Mapping(target = "products", source = "orderProducts")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "products", source = "orderProducts")
    OrderOutDto toDto(Order order);

    OrderOutDtoMIni toDtoMini(Order order);

    //UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order update(@MappingTarget Order targer, Order source);
}
