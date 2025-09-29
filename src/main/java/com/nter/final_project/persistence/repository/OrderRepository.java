package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
