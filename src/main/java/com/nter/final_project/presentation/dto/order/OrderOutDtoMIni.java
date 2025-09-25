package com.nter.final_project.presentation.dto.order;

import com.nter.final_project.persistence.entity.StatusOrder;

import java.time.LocalDate;

public record OrderOutDtoMIni(
        Long id,
        StatusOrder status,
        LocalDate createdAt
) {
}
