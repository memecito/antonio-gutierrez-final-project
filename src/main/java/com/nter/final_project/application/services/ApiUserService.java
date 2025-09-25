package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ApiUserService {

    Page<ApiUser> getAll(int pageNumber,int pageSize);
    ApiUser getById(Long id);
    ApiUser getByName(String name);
    ApiUser created(ApiUser apiUser);
    ApiUser update(Long id, ApiUser apiUser);
    void deleted(Long id);
}
