package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

class JwtServiceTest {

    @Mock
    private UserDetails userDetails;

    @Test
    void generateAccessToken() {
    }

    @Test
    void generateRefreshToken() {
        /*
        String accessType="";

        Map<String, Object> claims= new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        claims.put("access_type", accessType);
        when(Jwts.builder().compact()).thenReturn(DataProviders.tokenMock());

        String tokenResult=

         */
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