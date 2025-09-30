package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderProductMapper;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrdersProducts;
import com.nter.final_project.persistence.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository productRepository;
    private final OrderProductMapper mapper;
    @Override
    public Set<OrdersProducts> getAll() {
        return Set.of();
    }

    @Override
    public OrdersProducts getById(Long id) {
        return null;
    }

    @Override
    public OrdersProducts getByName(String name) {
        return null;
    }

    @Override
    @Transactional
    public List<OrdersProducts> created(Order order) {

        order.getOrdersProducts().forEach(orderProduct ->
                orderProduct.getOrderProductId().setOrder(order));
        return productRepository.saveAll(order.getOrdersProducts());

    }

    @Override
    public OrdersProducts update(Long id, OrdersProducts OrderProduct) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
