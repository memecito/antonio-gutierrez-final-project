package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Country;
import org.springframework.data.domain.Page;

public interface CountryService {
    Page<Country> getAll(int pageNumber, int pageSize);
    Country getByCode(String code);
    Country getByName(String name);
    Country created(Country Country);
    Country update(Long id, Country Country);
    void deleted(String code);
}
