package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.ApiUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    Page<ApiUser> findByIsActiveTrue(Pageable pageable);

    Optional<ApiUser> findByEmail(String email);
    Optional<List<ApiUser>> findByFullName(String fulName);
}
