package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository {
    Page<Product> findByStatusNot(StatusProduct status, Pageable pageable);

    Optional<Product> findByName(String name);
}
