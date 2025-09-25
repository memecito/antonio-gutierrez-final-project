package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Product;

import java.util.Set;

public interface ProductService {
    Set<Product> getAll();
    Product getById(Long id);
    Product getByName(String name);
    Product created(Product Product);
    Product update(Long id, Product Product);
    void deleted(Long id);
}
