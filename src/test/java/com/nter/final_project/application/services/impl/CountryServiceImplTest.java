package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UnsuportedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.persistence.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
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
    void getByCodeExist(){
        String message="pais no encontrado, CS01";
        when(countryRepository.findByCode(anyString())).thenReturn(Optional.empty());
        Exception exception= assertThrows(EntityNotFoundException.class,
                ()->countryService.getByCode(anyString()));

        assertEquals(message,exception.getMessage());
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
    void getByNameExist(){
        String name= "España";
        String message="pais no encontrado, CS02";
        when(countryRepository.findByName(any(String.class)))
                .thenReturn(Optional.empty());
        Exception exception= assertThrows(EntityNotFoundException.class,
                ()->countryService.getByName(name));
        assertEquals(message,exception.getMessage());
    }

    @Test
    void created() {
        String code= "es";
        String name= "España";
        Country newCountry= new Country("es","España");
        when(countryRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(countryRepository.findByName(anyString())).thenReturn(Optional.empty());

        when(countryRepository.save(any(Country.class))).thenReturn(newCountry);

        Country countryTest= countryService.created(newCountry);

        assertNotNull(countryTest);
    }




    @Test
    void createdFindByCode(){
        String code= "es";
        String name= "España";
        Country newCountry= new Country(code,name);
        String message="este codigo de pais ya esta en uso, CS03";

        when(countryRepository.findByCode("ES")).thenReturn(Optional.of(new Country()));

        EntityDuplicateException exception= assertThrows(EntityDuplicateException.class,
                ()-> countryService.created(newCountry));

        assertEquals(message,exception.getMessage());

    }

    @Test
    void createdFindCode(){
        // --- Arrange: Configurar el escenario ---

        // SOLUCIÓN: Crear un objeto Country con los datos necesarios para la prueba.
        // Aunque esperamos un error, el método necesita un código para trabajar.
        Country countryToCreate = new Country();
        countryToCreate.setCode("es"); // <--- ¡ESTA ES LA LÍNEA CLAVE!
        countryToCreate.setName("Spain");

        // 1. Simulamos que el repositorio SÍ encuentra un país con el código "ES"
        //    (el resultado de "es".toUpperCase())
        when(countryRepository.findByCode("ES")).thenReturn(Optional.of(new Country()));

        // --- Act & Assert: Ejecutar y verificar ---
        // 2. Verificamos que al llamar al método, se lanza la excepción esperada
        EntityDuplicateException exception = assertThrows(EntityDuplicateException.class, () -> {
            countryService.created(countryToCreate); // Pasamos el objeto con datos
        });

        // 3. Comprobamos que el mensaje de la excepción es el correcto
        assertEquals("este codigo de pais ya esta en uso, CS03", exception.getMessage());

        // 4. Verificamos que los otros métodos del repositorio nunca fueron llamados
        verify(countryRepository, never()).findByName(any(String.class));
        verify(countryRepository, never()).save(any(Country.class));
    }

    @Test
    void createdFindName(){
        Country country= new Country();
        country.setCode("es");
        country.setName("España");

        String message="este nombre de pais ya esta en uso, CS04";



    }


    @Test
    void update() {
        String code= "ES";
        Country country= DataProviders.countryMock();

        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));
        when(countryMapped.update(any(Country.class),any(Country.class))).thenReturn(country);

        Country countryResult= countryService.update(code,country);

        assertNotNull(countryResult);

    }

    @Test
    void deleted() {
        String code= "ES";
        Country country= DataProviders.countryMock();
        country.setApiUsers(Collections.emptyList());

       when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

       doNothing().when(countryRepository).deleteByCode(code);

        countryService.deleted(code);

        verify(countryRepository, times(1)).deleteByCode(code);

    }

    @Test
    void deletedException(){
        String code= "ES";
        Country country= DataProviders.countryMock();
        country.setApiUsers(DataProviders.userlListMock());

        String message= "No puede borrar el pais, contiene usuarios, CS05";
        when(countryRepository.findByCode(code)).thenReturn(Optional.of(country));

        Exception exception= assertThrows(UnsuportedException.class,
                ()->countryService.deleted(code));

        assertEquals(message,exception.getMessage());


    }
}