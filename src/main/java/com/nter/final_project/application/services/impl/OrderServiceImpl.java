package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.OrderProductId;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapped orderMapped;

    private final OrderProductService productService;

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getByDate(LocalDateTime starDate, LocalDateTime endDate, int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        return orderRepository.findByCreatedAtBetween(starDate,endDate,pageable);
    }

    @Override
    public Set<Order> getByUser(Long id) {
        return orderRepository.findByUser_Id(id);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Orden no encontrada, OS01")
        );
    }

    @Override
    @Transactional
    public Order created(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(StatusOrder.PROCESSING);
        Set<OrderProduct> orderProducts= order.getOrderProducts();
        //order.setOrderProducts(null);
        orderRepository.save(order);
        productService.created(order);
        return order;
    }

    @Override
    @Transactional
    public Order update(Long id, Order Order) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
