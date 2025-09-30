package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
