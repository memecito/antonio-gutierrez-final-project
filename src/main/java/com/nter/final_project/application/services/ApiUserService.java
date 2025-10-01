package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ApiUserService {

    Page<ApiUser> getAll(int pageNumber,int pageSize);
    @Named("getUserById")
    ApiUser getById(Long id);
    List<ApiUser> getByName(String name);
    ApiUser getByEmail(String email);
    ApiUser created(ApiUser apiUser);
    ApiUser update(Long id, ApiUser apiUser);
    ApiUser updateCountry(Long id, Country country);
    ApiUser updateStatus(Long id);
    void deleted(Long id);
}
