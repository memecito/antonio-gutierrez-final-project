package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Email(String email, Pageable pageable);
    Set<Order> findByUser_Email(String email);

    Set<Order> findByOrderProducts_OrderProductId_Product(Product product);

   // Page<Order> findByCreatedAtBetween(LocalDateTime createdAtStart, LocalDateTime createdAtEnd, Pageable pageable);

    //Page<Order> findByUser_Id(Long id, Pageable pageable);

}
