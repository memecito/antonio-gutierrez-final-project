package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.persistence.entity.Country;

import java.util.Set;

public class CountryServiceImpl implements CountryService {
    @Override
    public Set<Country> getAll() {
        return Set.of();
    }

    @Override
    public Country getById(Long id) {
        return null;
    }

    @Override
    public Country getByName(String name) {
        return null;
    }

    @Override
    public Country created(Country Country) {
        return null;
    }

    @Override
    public Country update(Long id, Country Country) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
