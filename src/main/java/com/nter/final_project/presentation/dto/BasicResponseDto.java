package com.nter.final_project.presentation.dto;

import lombok.Builder;

@Builder
public record BasicResponseDto(
        int status,
        String message
) {
}
