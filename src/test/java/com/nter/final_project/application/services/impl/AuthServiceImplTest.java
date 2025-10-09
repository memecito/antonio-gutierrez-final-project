package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.UnauthenticatedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private ApiUserServiceImpl apiUserService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;


    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void autentificate() {
        ApiUser user= DataProviders.userMock();

        when(apiUserService.getByEmail(anyString())).thenReturn(DataProviders.userMock());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(DataProviders.userDetailsMock());
        when(jwtService.generateAccessToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(jwtService.generateRefreshToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
        AuthToken token= authService.autentificate(user);

        assertNotNull(token);
    }

    @Test
    void autentificateException(){
        ApiUser user= DataProviders.userMock();

        String message= "Invalid credentails, APS07";

        when(apiUserService.getByEmail(anyString())).thenReturn(DataProviders.userMock());
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);

        Exception exception= assertThrows(BadRequestException.class,
                ()->authService.autentificate(user));

        assertEquals(message,exception.getMessage());

    }


    @Test
    void register() {
        ApiUser user= DataProviders.userMock();
        when(apiUserService.created(user)).thenReturn(user);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(DataProviders.userDetailsMock());
        when(jwtService.generateAccessToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(jwtService.generateRefreshToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());


        AuthToken token= authService.register(user);

        assertNotNull(token);

    }

    @Test
    void refreshException() {


        when(request.getCookies()).thenReturn(null);
        String message= "No refresh token present, APS08";

        //AuthToken token= authService.refresh(any(HttpServletRequest.class));

        Exception exception= assertThrows(UnauthenticatedException.class,
                ()->authService.refresh(any(HttpServletRequest.class)));
        assertEquals(message,exception.getMessage());

    }
}