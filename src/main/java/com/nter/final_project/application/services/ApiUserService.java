package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.ApiUser;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
@Mapper
public interface ApiUserService {

    Page<ApiUser> getAll(int pageNumber,int pageSize);
    @Named("getUserById")
    ApiUser getById(Long id);
    List<ApiUser> getByName(String name);
    ApiUser getByEmail(String email);
    ApiUser created(ApiUser apiUser);
    ApiUser update(Long id, ApiUser apiUser);
    void deleted(Long id);
}
