package com.nter.final_project.application.mappers;

import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.presentation.dto.product.ProductInDto;
import com.nter.final_project.presentation.dto.product.ProductOutDto;
import com.nter.final_project.presentation.dto.product.ProductOutDtoMIni;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderService.class,
                OrderMapped.class})
public interface ProductMapped {

    //INPUT
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    Product toModel(ProductInDto product);

    //OUPUT
    ProductOutDtoMIni toDtoMini(Product product);

    ProductOutDto toDto(Product product);

    //METODS
    Product update(@MappingTarget Product target, Product source);
}
