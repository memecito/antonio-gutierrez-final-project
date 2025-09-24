package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
