package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.ApiUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    Optional<ApiUser> findByEmail(String email);

    Page<ApiUser> findByActiveTrue(Pageable pageable);

    Optional<List<ApiUser>> findByFullName(String fulName);
}
