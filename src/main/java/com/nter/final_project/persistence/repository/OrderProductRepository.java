package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.OrderProductId;
import com.nter.final_project.persistence.entity.OrdersProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrdersProducts, OrderProductId> {
}
