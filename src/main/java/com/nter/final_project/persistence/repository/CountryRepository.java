package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
