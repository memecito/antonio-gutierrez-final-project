package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    @Override
    public Page<OrderProduct> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return orderProductRepository.findAll(pageable);
    }

    @Override
    public OrderProduct getById(Long id) {

        return null;
    }

    @Override
    public OrderProduct created(OrderProduct OrderProduct) {
        return null;
    }

    @Override
    public OrderProduct update(Long id, OrderProduct OrderProduct) {
        return null;
    }

    @Override
    public void deleted(Long id) {

    }
}
