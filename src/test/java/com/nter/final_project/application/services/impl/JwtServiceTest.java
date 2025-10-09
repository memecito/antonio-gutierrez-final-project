package com.nter.final_project.application.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final static String REFRESH_TOKEN_CLAIM = "refresh_token";
    //private final static String ACCESS_TOKEN_CLAIM = "access_token";

    @Mock
    private UserDetails userDetails;

    @Mock
    private ApiUserServiceImpl apiUserService;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateAccessToken() {

    }

    @Test
    void generateRefreshToken() {

    }

    @Test
    void extractUsername() {
    }

    @Test
    void isValidAccessToken() {
    }

    @Test
    void isValidRefreshToken() {
    }

    @Test
    void extractUser() {
    }

    @Test
    void authorization() {
    }

    @Test
    void getAccessTokenExpiration() {
    }

    @Test
    void isSecureAccess() {
    }
}