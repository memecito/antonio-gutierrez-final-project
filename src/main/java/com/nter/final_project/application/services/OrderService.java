package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<Order> getAll(int pageNumber, int pageSize);
    Order getById(Long id);
    Order getByName(String name);
    Order created(Order Order);
    Order update(Long id, Order Order);
    void deleted(Long id);
}
