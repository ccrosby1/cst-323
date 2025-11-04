/**
 * WorkoutRepository.java
 * 
 * Repository interface for workout data access
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.repository;

import com.example.workouttracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findByUser_UserId(int userId);
}
