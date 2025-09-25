package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;

import java.util.Set;

public interface OrderService {
    Set<Order> getAll();
    Order getById(Long id);
    Order getByName(String name);
    Order created(Order Order);
    Order update(Long id, Order Order);
    void deleted(Long id);
}
