package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.OrderProduct;
import org.springframework.data.domain.Page;

public interface OrderProductService {
    Page<OrderProduct> getAll(int pageNumber, int pageSize);
    OrderProduct getById(Long id);
    OrderProduct created(OrderProduct OrderProduct);
    OrderProduct update(Long id, OrderProduct OrderProduct);
    void deleted(Long id);
}
