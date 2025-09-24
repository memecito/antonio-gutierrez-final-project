package com.nter.final_project.persistence.repository;

import com.nter.final_project.persistence.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
}
