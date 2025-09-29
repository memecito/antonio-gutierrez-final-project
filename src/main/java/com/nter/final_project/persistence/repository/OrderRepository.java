package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.Order;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCreatedAtBetween(LocalDateTime createdAtStart, LocalDateTime createdAtEnd, Pageable pageable);

    Set<Order> findByUser_Id(Long id);
}
