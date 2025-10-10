package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UnsuportedException;
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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        Page<Country> countries = DataProviders.pageCountryMock();
        Pageable pageable = PageRequest.of(0, 5);

        when(countryRepository.findAll(pageable)).thenReturn(countries);
        Page<Country> countriesResutl = countryService.getAll(0, 5);

        assertNotNull(countriesResutl);
        assertFalse(countriesResutl.isEmpty());

    }

    @Test
    void getByCode() {
        String code = "ES";
        Country country = DataProviders.countryMock();

        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

        Country countryTest = countryService.getByCode(code);

        assertNotNull(countryTest);

    }

    @Test
    void getByCodeExist() {
        String message = "pais no encontrado, CS01";
        when(countryRepository.findByCode(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> countryService.getByCode(anyString()));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void getByName() {
        String name = "España";
        Country country = DataProviders.countryMock();
        when(countryRepository.findByName(name)).thenReturn(Optional.of(country));
        Country countryTest = countryService.getByName(name);
        assertNotNull(countryTest);
    }

    @Test
    void getByNameExist() {
        String name = "España";
        String message = "pais no encontrado, CS02";
        when(countryRepository.findByName(any(String.class)))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> countryService.getByName(name));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void created() {
        String code = "ES";
        String name = "España";
        Country newCountry = new Country();
        newCountry.setCode("es");
        newCountry.setName(name);

        when(countryRepository.findByCode("ES")).thenReturn(Optional.empty());
        when(countryRepository.findByName(anyString())).thenReturn(Optional.empty());

        when(countryRepository.save(any(Country.class))).thenReturn(newCountry);

        Country countryTest = countryService.created(newCountry);

        assertNotNull(countryTest);
    }


    @Test
    void createdFindByCode() {
        String code = "ES";
        String name = "España";
        Country newCountry = new Country();
        newCountry.setCode("es");
        newCountry.setName(name);
        String message = "este codigo de pais ya esta en uso, CS03";

        when(countryRepository.findByCode("ES")).thenReturn(Optional.of(newCountry));

        Exception exception = assertThrows(EntityDuplicateException.class,
                () -> countryService.created(newCountry));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void createdFindName() {
        Country country = new Country();
        country.setCode("es");
        country.setName("España");

        String message = "este nombre de pais ya esta en uso, CS04";


    }


    @Test
    void update() {
        String code = "ES";
        Country country = DataProviders.countryMock();

        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));
        when(countryMapped.update(any(Country.class), any(Country.class))).thenReturn(country);

        Country countryResult = countryService.update(code, country);

        assertNotNull(countryResult);

    }

    @Test
    void deleted() {
        String code = "ES";
        Country country = DataProviders.countryMock();
        country.setApiUsers(Collections.emptyList());

        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

        doNothing().when(countryRepository).deleteByCode(code);

        countryService.deleted(code);

        verify(countryRepository, times(1)).deleteByCode(code);

    }

    @Test
    void deletedException() {
        String code = "ES";
        Country country = DataProviders.countryMock();
        country.setApiUsers(DataProviders.userlListMock());

        String message = "No puede borrar el pais, contiene usuarios, CS05";
        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

        Exception exception = assertThrows(UnsuportedException.class,
                () -> countryService.deleted(code));

        assertEquals(message, exception.getMessage());


    }
}