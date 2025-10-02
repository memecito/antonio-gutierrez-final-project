package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.presentation.dto.order.OrderStatusInDto;
import com.nter.final_project.presentation.dto.product.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderService.class,
                OrderMapped.class})
public interface ProductMapped {

    //INPUT
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    @Mapping(target = "status", ignore = true, defaultValue = "available")
    Product toModel(ProductInDto product);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    @Mapping(target = "status", ignore = true)
    Product toModelUpdate(ProductUpdateDto productUpdateDto);

    default String toModelStatus( ProductStatusInDto statusInDto){
        if (statusInDto == null) {
            return null;
        }
        return statusInDto.getStatus();
    }

    //OUPUT

    ProductOutDtoMIni toDtoMini(Product product);

    ProductOutDto toDto(Product product);

    //METODS
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product update(@MappingTarget Product target, Product source);
}
