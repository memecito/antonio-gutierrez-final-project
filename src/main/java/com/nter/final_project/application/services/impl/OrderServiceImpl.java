package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapped orderMapped;

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getById(Long id) {

        return orderRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Orden no encontrada, OS01")
        );
    }

    @Override
    public Page<Order> getByCreatedAt(LocalDateTime date) {
        return null;
    }

    @Override
    public Order created(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        Order orderFound= getById(id);
        return orderMapped.update(order, orderFound);
    }
}
