package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCode(String code);

    Optional<Country> findByName(String name);

    void deleteByCode(String code);
}
