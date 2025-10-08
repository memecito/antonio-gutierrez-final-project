package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private ApiUserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {

        ApiUser user= DataProviders.userMock();
        String[] rolAdmin= new String[]{"ADMIN", "USER"};

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername("email")).thenReturn(DataProviders.userDetailsMock());

        UserDetails userResult= userDetailsService.loadUserByUsername("email");

        assertNotNull(userResult);

    }
}