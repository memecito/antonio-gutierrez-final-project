package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UserNotFounException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.repository.ApiUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;

    @Override
    public Page<ApiUser> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return apiUserRepository.findAll(pageable);
    }

    @Override
    public ApiUser getById(Long id) {

        return apiUserRepository.findById(id).orElseThrow(
                () -> new UserNotFounException("Usuario con id: " + id + " no encontrado, APS01")
        );
    }

    @Override
    public List<ApiUser> getByName(String name) {
        return apiUserRepository.findByFullName(name).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre")
        );
    }

    @Override
    public ApiUser getByEmail(String email) {
        return apiUserRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado ningun usuario con ese nombre")
        );
    }

    @Override
    public ApiUser created(ApiUser apiUser) {
        return null;
    }

    @Override
    public ApiUser update(Long id, ApiUser apiUser) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
