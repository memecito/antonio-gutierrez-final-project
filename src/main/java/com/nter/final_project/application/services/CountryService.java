package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Country;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

public interface CountryService {
    Page<Country> getAll(int pageNumber, int pageSize);

    @Named("getCountryByCode")
    Country getByCode(String code);

    Country getByName(String name);

    Country created(Country Country);

    Country update(String code, Country Country);

    void deleted(String code);
}
