package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.Product;

import java.util.Set;

public class ProductServiceImpl implements ProductService {
    @Override
    public Set<Product> getAll() {
        return Set.of();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product getByName(String name) {
        return null;
    }

    @Override
    public Product created(Product Product) {
        return null;
    }

    @Override
    public Product update(Long id, Product Product) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
