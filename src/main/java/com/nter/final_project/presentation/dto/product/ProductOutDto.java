package com.nter.final_project.presentation.dto.product;

import com.nter.final_project.persistence.entity.StatusProduct;

import java.time.LocalDate;

public record ProductOutDto(
        Long id,
        String name,
        Double price,
        StatusProduct status,
        LocalDate created
) {
}
