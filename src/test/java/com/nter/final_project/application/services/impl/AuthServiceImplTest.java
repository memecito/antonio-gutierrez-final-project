package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.UnauthenticatedException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ApiUserServiceImpl apiUserService;

    @Spy
    @InjectMocks
    private AuthServiceImpl authService;

    public final String REFRESH_TOKEN_COOKIE = "refreshToken";


    @Test
    void autentificate() {
        ApiUser user = DataProviders.userMock();

        when(apiUserService.getByEmail(anyString())).thenReturn(DataProviders.userMock());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(DataProviders.userDetailsMock());
        when(jwtService.generateAccessToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(jwtService.generateRefreshToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        AuthToken token = authService.autentificate(user);

        assertNotNull(token);
    }

    @Test
    void autentificateException() {
        ApiUser user = DataProviders.userMock();

        String message = "Invalid credentails, APS07";

        when(apiUserService.getByEmail(anyString())).thenReturn(DataProviders.userMock());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(BadRequestException.class,
                () -> authService.autentificate(user));

        assertEquals(message, exception.getMessage());

    }


    @Test
    void register() {
        ApiUser user = DataProviders.userMock();
        when(apiUserService.created(user)).thenReturn(user);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(DataProviders.userDetailsMock());
        when(jwtService.generateAccessToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());
        when(jwtService.generateRefreshToken(any(UserDetails.class))).thenReturn(DataProviders.tokenMock());


        AuthToken token = authService.register(user);

        assertNotNull(token);

    }

    @Test
    void refresh() {
        String validTokenValue = "validToken";
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newResfreshToken";

        ApiUser user = DataProviders.userMock();

        Cookie refreshCookie = new Cookie(REFRESH_TOKEN_COOKIE, validTokenValue);
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});

        when(jwtService.extractUsername(validTokenValue)).thenReturn(user.getEmail());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(DataProviders.userDetailsMock());

        when(jwtService.isValidRefreshToken(validTokenValue, DataProviders.userDetailsMock())).thenReturn(true);

        when(jwtService.generateAccessToken(DataProviders.userDetailsMock())).thenReturn(newAccessToken);
        when(jwtService.generateRefreshToken(DataProviders.userDetailsMock())).thenReturn(newRefreshToken);

        AuthToken tokenResult = authService.refresh(request);

        assertNotNull(tokenResult);


    }


    @Test
    void refreshUnauthenticated() {
        when(request.getCookies()).thenReturn(null);

        String message = "No refresh token present, APS08";

        Exception exception = assertThrows(UnauthenticatedException.class,
                () -> authService.refresh(request));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void refreshTokenMissing() {

        Cookie[] cookie = {
                new Cookie("galleta01", "value"),
                new Cookie("galleta02", "valor")};
        when(request.getCookies()).thenReturn(cookie);


        String message = "Refresh token missing, APS09";


        Exception exception = assertThrows(UnauthenticatedException.class,
                () -> authService.refresh(request));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void testHavePermissionId() {
        Long id=1L;
        ApiUser user= DataProviders.userMock();
        user.setId(id);
        user.setAdmin(false);

        doReturn(user).when(authService).currentUser();

        assertThrows(UnauthorizedException.class,
                ()->authService.havePermision(id));
    }
    @Test
    void testHavePermissionIdEx() {
        Long id=1L;
        ApiUser user= DataProviders.userMock();
        user.setId(id);
        user.setAdmin(true);

        doReturn(user).when(authService).currentUser();

        assertThrows(UnauthorizedException.class,
                ()->authService.havePermision(2L));
    }

    @Test
    void testHavePermissionEmail() {
        Long id=1L;
        String mail= "mail@mail.com";
        ApiUser user= DataProviders.userMock();
        user.setId(id);
        user.setAdmin(false);
        user.setEmail(mail);

        doReturn(user).when(authService).currentUser();

        assertThrows(UnauthorizedException.class,
                ()->authService.havePermision(mail));

    }
    @Test
    void testHavePermissionEmailEx() {
        Long id=1L;
        String mail= "mail@mail.com";
        ApiUser user= DataProviders.userMock();
        user.setId(id);
        user.setAdmin(true);
        user.setEmail("rue@mail");

        doReturn(user).when(authService).currentUser();

        assertThrows(UnauthorizedException.class,
                ()->authService.havePermision(mail));

    }
}
