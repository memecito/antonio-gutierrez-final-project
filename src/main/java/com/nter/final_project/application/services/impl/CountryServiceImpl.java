package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.persistence.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapped countryMapped;

    @Override
    public Page<Country> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return countryRepository.findAll(pageable);
    }

    @Override
    public Country getByCode(String code) {
        return countryRepository.findByCode(code.toUpperCase()).orElseThrow(
                () -> new EntityNotFoundException("pais no encontrado, CS01")
        );
    }

    @Override
    public Country getByName(String name) {

        return countryRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("pais no encontrado, CS02")
        );
    }

    @Override
    @Transactional
    public Country created(Country country) {
        if (countryRepository.findByCode(country.getCode().toUpperCase()).isPresent())
            throw new EntityDuplicateException("este codigo de pais ya esta en uso, CS03");
        if (countryRepository.findByName(country.getName()).isPresent())
            throw new EntityDuplicateException("este nombre de pais ya esta en uso, CS04");
        country.setCode(country.getCode().toUpperCase());
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public Country update(String code, Country country) {
        Country cFound = getByCode(code);
        return countryMapped.update(cFound, country);
    }

    @Override
    @Transactional
    public void deleted(String code) {
        getByCode(code);
        countryRepository.deleteByCode(code);
    }
}
