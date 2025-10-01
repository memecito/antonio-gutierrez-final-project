package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderProductMapper;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.OrderProductId;
import com.nter.final_project.persistence.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository productRepository;
    private final OrderProductMapper mapper;

    @Override
    public Set<OrderProduct> getAll() {
        return (Set<OrderProduct>) productRepository.findAll();
    }

    @Override
    public OrderProduct getById(OrderProductId id) {

        return productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Orden no encontrada, OPS01")
        );
    }

    @Override
    @Transactional
    public List<OrderProduct> created(Order order) {
        order.getOrderProducts().forEach(orderProduct ->
                orderProduct.getOrderProductId().setOrder(order));
        return productRepository.saveAll(order.getOrderProducts());

    }

    @Override
    @Transactional
    public OrderProduct update(OrderProductId id, OrderProduct orderProduct) {
        OrderProduct orderProductFound= getById(id);
        return mapper.update(orderProductFound, orderProduct);
    }

}
