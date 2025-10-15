package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthToken autentificate(ApiUser user);

    AuthToken register(ApiUser user);

    AuthToken refresh(HttpServletRequest request);

    boolean authorization(Long id, String token);

    boolean isOwner(Long id);
}
