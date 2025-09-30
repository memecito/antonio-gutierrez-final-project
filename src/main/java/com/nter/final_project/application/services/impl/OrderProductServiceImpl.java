package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderProductMapper;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository productRepository;
    private final OrderProductMapper mapper;
    @Override
    public Set<OrderProduct> getAll() {
        return Set.of();
    }

    @Override
    public OrderProduct getById(Long id) {
        return null;
    }

    @Override
    public OrderProduct getByName(String name) {
        return null;
    }

    @Override
    @Transactional
    public List<OrderProduct> created(Order order) {
        order.getOrderProducts().forEach(orderProduct -> orderProduct.setOrder(order));
        return productRepository.saveAll(order.getOrderProducts());

    }

    @Override
    public OrderProduct update(Long id, OrderProduct OrderProduct) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
