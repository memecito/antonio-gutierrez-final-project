package com.nter.final_project.application.mappers;

import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.presentation.dto.product.ProductInDto;
import com.nter.final_project.presentation.dto.product.ProductOutDtoMIni;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapped {

    //INPUT
    Product toModel(ProductInDto product);
    //OUPUT
    ProductOutDtoMIni toDtoMini(Product product);
    //METODS
    Product update(@MappingTarget Product target, Product source);
}
