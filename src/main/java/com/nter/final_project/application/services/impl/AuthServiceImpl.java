package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.application.services.AuthService;
import com.nter.final_project.exception.*;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    private final ApiUserService apiUserService;
    public final static String REFRESH_TOKEN_COOKIE = "refreshToken";


    /***
     *
     * @param user
     * @return
     */
    @Override
    public AuthToken autentificate(ApiUser user) {
        ApiUser userFound = apiUserService.getByEmail(user.getEmail());
        if(!userFound.getActive()) {
            log.warn("fallo autentificación user: {} desactivado",user.getEmail());
            throw new UserNotActived("Usuario no activo,APS11");
        }
        if (!passwordEncoder.matches(user.getPassword(), userFound.getPassword())) {
            log.warn("fallo autentificación user: {} contraseña erronea", user.getEmail());
            throw new BadRequestException("Invalid credentails, APS07");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userFound.getEmail());

        return new AuthToken(
                jwtService.generateAccessToken(userDetails),
                jwtService.generateRefreshToken(userDetails)
        );
    }


    /***
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public AuthToken register(ApiUser user) {

        ApiUser userRegister = apiUserService.created(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRegister.getEmail());
        log.info("Usuario registrado {}", user.getEmail());
        return new AuthToken(
                jwtService.generateAccessToken(userDetails),
                jwtService.generateRefreshToken(userDetails)

        );
    }

    @Override
    public AuthToken refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            throw new UnauthenticatedException("No refresh token present, APS08");

        String refreshToken = Arrays.stream(cookies)
                .filter(c -> REFRESH_TOKEN_COOKIE.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new UnauthenticatedException("Refresh token missing, APS09"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(refreshToken));

        if (!jwtService.isValidRefreshToken(refreshToken, userDetails))
            throw new InvalidTokenException("Invalid or expired refresh token, APS10");

        return new AuthToken(
                jwtService.generateAccessToken(userDetails),
                jwtService.generateRefreshToken(userDetails)
        );
    }

    @Override
    public boolean havePermision(String email){
        ApiUser userLogin= currentUser();
        if(userLogin.getAdmin()) return true;
        if(!userLogin.getEmail().equals(email)) throw new UnauthorizedException("no tiene permiso, AS11");
        return true;
    }
    @Override
    public boolean havePermision(Long id){
        ApiUser userLogin= currentUser();
        if(userLogin.getAdmin()) return true;
        if(!userLogin.getId().equals(id)) throw new UnauthorizedException("no tiene permiso, AS11");
        return true;
    }

    public ApiUser currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {

            return null;
        }
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return apiUserService.getByEmail(((UserDetails) principal).getUsername());
        }
        throw new IllegalStateException("El objeto principal del usuario no es del tipo esperado (ApiUser).");
    }


}
