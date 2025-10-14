package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApiUserService {


    Page<ApiUser> getAll(int pageNumber, int pageSize);

    Page<ApiUser> getActive(int pageNumber, int pageSize);

    ApiUser getById(Long id, String authHeader);

    @Named("getUserById")
    ApiUser getById(Long id);

    List<ApiUser> getByName(String name);

    ApiUser getByEmail(String email);

    ApiUser created(ApiUser apiUser);

    ApiUser update(Long id, ApiUser apiUser, String token);

    void updatePassword(Long id, String password, String token);

    ApiUser updateCountry(Long id, Country country, String token);

    ApiUser updateAdmin(Long id);

    ApiUser downgroudnAdmin(Long id);

    ApiUser statusDesactive(Long id);

    ApiUser statusActived(Long id);

    void deleted(Long id);
}
