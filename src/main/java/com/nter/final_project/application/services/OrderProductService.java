package com.nter.final_project.application.services;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.OrderProductId;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface OrderProductService {
    Set<OrderProduct> getAll();
    OrderProduct getById(OrderProductId id);
    List<OrderProduct> created(Order oder);
    OrderProduct update(OrderProductId id, OrderProduct orderProduct);
    void delete(OrderProductId id);
}
