package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.Order;

import java.util.Set;

public class OrderServiceImpl implements OrderService {
    @Override
    public Set<Order> getAll() {
        return Set.of();
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public Order getByName(String name) {
        return null;
    }

    @Override
    public Order created(Order Order) {
        return null;
    }

    @Override
    public Order update(Long id, Order Order) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
