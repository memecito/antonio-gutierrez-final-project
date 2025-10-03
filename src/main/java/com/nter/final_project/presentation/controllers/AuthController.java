package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.application.services.impl.JwtService;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.auth.AuthInDto;
import com.nter.final_project.presentation.dto.auth.AuthOutDto;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final ApiUserService apiUserService;
    public final ApiUserMapped apiUserMapped;
public final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthOutDto> created(@Valid @RequestBody ApiUserInDto apiUser) {
        AuthToken token=apiUserService.register(apiUserMapped.toModel(apiUser));
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
        AuthToken token= apiUserService.autentificate(apiUserMapped.toModelAuth(authInDto));

        return ResponseEntity.ok(
                new AuthOutDto(
                        HttpStatus.CREATED.value(),
                        token.accestToken(),
                        jwtService.getAccessTokenExpiration()
                )
        );    }
}
