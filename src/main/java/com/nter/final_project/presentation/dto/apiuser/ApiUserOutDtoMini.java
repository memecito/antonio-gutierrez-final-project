package com.nter.final_project.presentation.dto.apiuser;

import java.time.LocalDate;

public record ApiUserOutDtoMini(
        Long id,
        String fullName,
        String email,
        LocalDate createdAt
) {
}
