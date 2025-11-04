/**
 * UserRepository.java
 * 
 * Repository interface for user data access
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workouttracker.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
