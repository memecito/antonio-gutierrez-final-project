package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Country;

import java.util.Set;

public interface CountryService {
    Set<Country> getAll();
    Country getById(Long id);
    Country getByName(String name);
    Country created(Country Country);
    Country update(Long id, Country Country);
    void deleted(Long id);
}
