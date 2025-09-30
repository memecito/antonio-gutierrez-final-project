package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrdersProducts;

import java.util.List;
import java.util.Set;

public interface OrderProductService {
    Set<OrdersProducts> getAll();
    OrdersProducts getById(Long id);
    OrdersProducts getByName(String name);
    List<OrdersProducts> created(Order oder);
    OrdersProducts update(Long id, OrdersProducts orderProduct);
    void deleted(Long id);
}
