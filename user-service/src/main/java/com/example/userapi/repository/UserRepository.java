package com.example.userapi.repository;

import com.example.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByActive(Boolean active);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

