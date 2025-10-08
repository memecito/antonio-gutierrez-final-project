package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.persistence.entity.ApiUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private ApiUserServiceImpl apiUserService;

    @Test
    void autentificate() {


    }

    @Test
    void register() {
    }

    @Test
    void refresh() {
    }
}