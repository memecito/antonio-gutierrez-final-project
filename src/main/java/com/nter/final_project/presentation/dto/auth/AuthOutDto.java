package com.nter.final_project.presentation.dto.auth;

public record AuthOutDto(
        int status,
        String token,
        String expiration
) {
}
