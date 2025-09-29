package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;

import java.util.List;
import java.util.Set;

public interface OrderProductService {
    Set<OrderProduct> getAll();
    OrderProduct getById(Long id);
    OrderProduct getByName(String name);
    List<OrderProduct> created(Order oder);
    OrderProduct update(Long id, OrderProduct orderProduct);
    void deleted(Long id);
}
