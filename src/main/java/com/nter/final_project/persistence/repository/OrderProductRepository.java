package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
