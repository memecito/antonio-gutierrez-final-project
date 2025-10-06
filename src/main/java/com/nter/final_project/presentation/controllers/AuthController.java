package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.AuthService;
import com.nter.final_project.application.services.impl.JwtService;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.auth.AuthInDto;
import com.nter.final_project.presentation.dto.auth.AuthOutDto;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final ApiUserMapped apiUserMapped;

    public final AuthService authService;
    public final JwtService jwtService;

    public final static String REFRESH_TOKEN_COOKIE = "refreshToken";

    @PostMapping("/register")
    public ResponseEntity<AuthOutDto> created(@Valid @RequestBody ApiUserInDto apiUser, HttpServletResponse response) {
        AuthToken token = authService.register(apiUserMapped.toModel(apiUser));
        setRefreshTokenCookie(response, token.refreshToken());

        return ResponseEntity.ok(
                new AuthOutDto(
                        HttpStatus.CREATED.value(),
                        token.accestToken(),
                        jwtService.getAccessTokenExpiration()
                )
        );
    }

    @PostMapping("/loggin")
    public ResponseEntity<AuthOutDto> login(@Valid @RequestBody AuthInDto authInDto, HttpServletResponse response) {
        AuthToken token = authService.autentificate(apiUserMapped.toModelAuth(authInDto));
        setRefreshTokenCookie(response, token.refreshToken());

        return ResponseEntity.ok(
                new AuthOutDto(
                        HttpStatus.CREATED.value(),
                        token.accestToken(),
                        jwtService.getAccessTokenExpiration()
                )
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthOutDto> refresh(HttpServletRequest request, HttpServletResponse response) {

        AuthToken authTokens = authService.refresh(request);
        setRefreshTokenCookie(response, authTokens.refreshToken());

        return ResponseEntity.ok(new AuthOutDto(HttpStatus.OK.value(),
                authTokens.accestToken(),
                jwtService.getAccessTokenExpiration()));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, refreshToken)
                .httpOnly(true)
                .secure(jwtService.isSecureAccess()) // En entornos productivos debería ir en true
                .path("/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

    }
}
