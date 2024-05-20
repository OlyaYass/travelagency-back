package com.example.agency.repository;

import com.example.agency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
