package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    Optional<ApiUser> findByEmail(String email);
    Optional<List<ApiUser>> findByFullName(String fulName);
}
