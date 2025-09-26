package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderService {
    Page<Order> getAll(int pageNumber, int pageSize);
    Order getById(Long id);
    Page<Order> getByCreatedAt(LocalDateTime date);
    Order created(Order Order);
    Order update(Long id, Order Order);
}
