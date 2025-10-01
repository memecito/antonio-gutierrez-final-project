package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Product;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ProductService {
    Page<Product> getAll(int pageNumber, int pageSize);

    @Named("getProductById")
    Product getById(Long id);

    Product getByName(String name);

    Page<Product> getByCriteria(Map<String, String> params, int page, int size);

    Product created(Product Product);

    Product update(Long id, Product Product);

    void deleted(Long id);
}
