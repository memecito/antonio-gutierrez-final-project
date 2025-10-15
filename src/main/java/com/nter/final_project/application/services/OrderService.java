package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.presentation.dto.PageResponse;
import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface OrderService {

    Page<Order> getAll(int pageNumber, int pageSize);

    Page<Order> getUsersOrders(int pageNumber, int pageSize);

    Set<Order> getByUser(String name);

    @Named("findOrderByProduct")
    Set<Order> getByProduct(Long id);

    @Named("findOrderById")
    Order getById(Long id);

    Order created(Order Order);

    Order update(Long id, Order order);

    Order updateStatus(Long id, String status);

    void deleted(Long id);

}
