package com.nter.final_project.presentation.dto.product;

import com.nter.final_project.persistence.entity.StatusProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ProductInDto {
    private String name;
    private Double price;
    private StatusProduct status;
}
