package com.example.agency.repository;

import com.example.agency.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

    boolean existsByName(String role);
}
