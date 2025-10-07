package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.persistence.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CountryMapped countryMapped;

    @InjectMocks
    private CountryServiceImpl countryService;


    @Test
    void getAll() {

        Page<Country> countries= DataProviders.pageCountryMock();
        Pageable pageable= PageRequest.of(0,5);

        when(countryRepository.findAll(pageable)).thenReturn(countries);
        Page<Country> countriesResutl= countryService.getAll(0,5);

        assertNotNull(countriesResutl);
        assertFalse(countriesResutl.isEmpty());

    }

    @Test
    void getByCode() {
        String code ="ES";
        Country country= DataProviders.countryMock();

        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

        Country countryTest= countryService.getByCode(code);

        assertNotNull(countryTest);

    }

    @Test
    void getByName() {
        String name= "España";
        Country country=DataProviders.countryMock();
        when(countryRepository.findByName(name)).thenReturn(Optional.of(country));
        Country countryTest= countryService.getByName(name);
        assertNotNull(countryTest);
    }

    @Test
    void created() {
        Country newCountry= DataProviders.countryMock();

        when(countryRepository.save(any(Country.class))).thenReturn(newCountry);

        Country countryTest= countryService.created(newCountry);

        assertNotNull(countryTest);
    }

    @Test
    void update() {
        String code= "ES";
        Country country= new Country("ES", "España");
        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));
        when(countryService.getByCode(code)).thenReturn(country);
        when(countryMapped.update(any(Country.class), country)).thenReturn(country);

        Country countryTest= countryService.update(code,country);

        assertNotNull(countryTest);
        verify(countryMapped).update(any(Country.class),any(Country.class));

    }

    @Test
    void deleted() {

    }
}