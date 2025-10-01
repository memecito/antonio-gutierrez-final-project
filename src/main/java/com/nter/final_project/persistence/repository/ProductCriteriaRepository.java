package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductCriteriaRepository {

    Page<Product> findProductByCustomParam(Map<String, String> params, Pageable pageable);
}
