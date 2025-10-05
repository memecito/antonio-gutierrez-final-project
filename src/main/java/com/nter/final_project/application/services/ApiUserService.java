package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.auth.AuthToken;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApiUserService {

    Page<ApiUser> getAll(int pageNumber,int pageSize);
    ApiUser getById(Long id, String authHeader);
    @Named("getUserById")
    ApiUser getById(Long id);
    List<ApiUser> getByName(String name);
    ApiUser getByEmail(String email);
    AuthToken sigin(AuthToken userToken);
    AuthToken autentificate (ApiUser user);
    AuthToken register(ApiUser user);
    ApiUser created(ApiUser apiUser);
    ApiUser update(Long id, ApiUser apiUser);
    ApiUser updateCountry(Long id, Country country);
    ApiUser statusDesactive(Long id);
    ApiUser statusActived(Long id);
    void deleted(Long id);
}
