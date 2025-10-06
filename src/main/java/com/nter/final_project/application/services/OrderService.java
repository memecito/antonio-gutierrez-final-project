package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;


public interface OrderService {

    Page<Order> getAll(int pageNumber, int pageSize);

    Page<Order> getUsersOrders(int pageNumber, int pageSize, String token);

    Page<Order> getByDate(LocalDateTime starDate, LocalDateTime endDate, int pageNumber, int pageSize);

    @Named("findOrderByProduct")
    Set<Order> getByProduct(Long id);

    @Named("findOrderById")
    Order getById(Long id,String reques);

    Order created(Order Order, String token);

    Order update(Long id, Order order, String token);

    Order updateStatus(Long id , String status);

    void deleted(Long id, String token);
}
