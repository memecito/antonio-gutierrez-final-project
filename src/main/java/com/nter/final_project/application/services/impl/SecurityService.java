package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ApiUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final ApiUserService userService;

    public boolean isOwner(Authentication authentication, Long useId){
        String userName=authentication.getName();
        return userService.getById(useId).getEmail().equals(userName);
    }
}
