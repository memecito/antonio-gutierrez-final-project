package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Set;


public interface OrderService {

    Page<Order> getAll(int pageNumber, int pageSize);

    Page<Order> getByDate(LocalDateTime starDate, LocalDateTime endDate, int pageNumber, int pageSize);

    @Named("findOrderByUser")
    Set<Order> getByUser(Long id);

    @Named("findOrderByProduct")
    Set<Order> getByProduct(Long id);

    @Named("findOrderById")
    Order getById(Long id);

    Order created(Order Order);

    Order update(Long id, Order Order);

    void deleted(Long id);
}
