package com.nter.final_project.presentation.dto.auth;

public record AuthToken(
        String accestToken,
        String refreshToken
) {
}
