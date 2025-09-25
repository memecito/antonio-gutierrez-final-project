package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.OrderProduct;

import java.util.Set;

public interface OrderProductService {
    Set<OrderProduct> getAll();
    OrderProduct getById(Long id);
    OrderProduct getByName(String name);
    OrderProduct created(OrderProduct OrderProduct);
    OrderProduct update(Long id, OrderProduct OrderProduct);
    void deleted(Long id);
}
