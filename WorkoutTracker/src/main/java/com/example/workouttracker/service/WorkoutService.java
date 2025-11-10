/**
 * WorkoutService.java
 * 
 * Service class for managing workout CRUD operations
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.workouttracker.model.User;
import com.example.workouttracker.model.Workout;
import com.example.workouttracker.repository.UserRepository;
import com.example.workouttracker.repository.WorkoutRepository;

@Service
public class WorkoutService {

	// Inject workout and user repositories
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a workout for specified user
     * @param workout workout data to save
     * @param username username of user
     */
    public void saveWorkout(Workout workout, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            workout.setUser(optionalUser.get()); // link workout to user
            workoutRepository.save(workout); // save workout
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
    
    /**
     * Retrieves all workouts for given username
     * @param username username of user
     * @return list of workouts, empty if user not found
     */
    public List<Workout> getWorkoutsByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> workoutRepository.findByUser_UserId(user.getUserId()))
                .orElseGet(List::of);
    }
}