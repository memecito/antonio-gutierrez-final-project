package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.persistence.entity.OrderProduct;

import java.util.Set;

public class OrderProductServiceImpl implements OrderProductService {
    @Override
    public Set<OrderProduct> getAll() {
        return Set.of();
    }

    @Override
    public OrderProduct getById(Long id) {
        return null;
    }

    @Override
    public OrderProduct getByName(String name) {
        return null;
    }

    @Override
    public OrderProduct created(OrderProduct OrderProduct) {
        return null;
    }

    @Override
    public OrderProduct update(Long id, OrderProduct OrderProduct) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
