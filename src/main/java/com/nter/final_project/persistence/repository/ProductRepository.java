package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findByNameLike(String name);
    Optional<Product> findByName(String name);

    List<Product> findByStatusNot(StatusProduct status, Pageable pageable);

}
