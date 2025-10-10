package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.persistence.entity.ApiUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private ApiUserServiceImpl apiUserService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        String mail= "mail@mail.com";
        ApiUser user= DataProviders.userMockUser();

        when(apiUserService.getByEmail(anyString())).thenReturn(user);

        UserDetails userResult= userDetailsService.loadUserByUsername(mail);

        assertNotNull(userResult);

    }

    @Test
    void loadUserByAdmin(){
        String mail= "mail@mail.com";
        ApiUser user= DataProviders.userMock();

        when(apiUserService.getByEmail(anyString())).thenReturn(user);

        UserDetails userResult= userDetailsService.loadUserByUsername(mail);

        assertNotNull(userResult);

    }
}