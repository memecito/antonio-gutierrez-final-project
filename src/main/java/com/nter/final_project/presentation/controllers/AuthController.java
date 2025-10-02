package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.auth.AuthInDto;
import com.nter.final_project.presentation.dto.auth.AuthOutDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public ResponseEntity<ApiUserOutDto> created(@Valid @RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.created(apiUserMapped.toModel(apiUser))));
    }

    @PostMapping("/loggin")
    public ResponseEntity<AuthOutDto> login(@Valid @RequestBody AuthInDto authInDto, HttpServletResponse response) {
        return null;
    }
}
