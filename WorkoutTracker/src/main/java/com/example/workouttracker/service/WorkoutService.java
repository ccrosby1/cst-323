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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.workouttracker.model.User;
import com.example.workouttracker.model.Workout;
import com.example.workouttracker.repository.UserRepository;
import com.example.workouttracker.repository.WorkoutRepository;

@Service
public class WorkoutService {

    // Logger for debugging/monitoring
    private static final Logger logger = LoggerFactory.getLogger(WorkoutService.class);
    
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
        
        // check if user exists
        if (optionalUser.isPresent()) {
            workout.setUser(optionalUser.get()); // link workout to user
            logger.debug("Attempting to save workout '{}' for user '{}'", workout, username);
            workoutRepository.save(workout); // save workout
            logger.info("Saved workout '{}' for user '{}'", workout, username);
        } else {
        	logger.error("Failed to save workout: User '{}' not found", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
    
    /**
     * Retrieves all workouts for given username
     * @param username username of user
     * @return list of workouts, empty if user not found
     */
    public List<Workout> getWorkoutsByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        
        // check if user exists
        if(optionalUser.isEmpty()) {
        	logger.warn("User '{}' not found, returning empty list", username);
        	return List.of(); // return empty list if user not found
        }
        
        // get workouts for user
        User user = optionalUser.get();
        logger.debug("Fetching workouts for user '{}'", username);
        List<Workout> workouts = workoutRepository.findByUser_UserIdOrderByDateDesc(user.getUserId());
        logger.info("Retrieved {} workouts for user '{}'", workouts.size(), username);
        return workouts;
    }
    
    /**
     * Delete workout by ID if it belongs to user
     * @param workoutId id of workout to delete
     * @param username username of user
     */
    public void deleteWorkoutById(int workoutId, String username) {
    	Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
    	
    	// check if workout exists
    	if (optionalWorkout.isPresent()) {
    		Workout workout = optionalWorkout.get();
    		// verify workout belongs to user
    		if(workout.getUser().getUsername().equals(username)) {
    			logger.debug("Attempting to delete workout '{}' for user '{}'", workoutId, username);
    			workoutRepository.deleteById(workoutId);
    			logger.info("Deleted workout '{}' by user '{}'", workoutId, username);
    		} else {
    			// unauthorized deletion attempt
    			logger.warn("Unauthorized deletion attempt by user '{}'", username);
    			throw new SecurityException("Unauthorized to delete this workout");
    		}
    	} else {
    		// workout not found
    		logger.error("Workout not found with ID: {}", workoutId);
    		throw new IllegalArgumentException("Workout not found with ID: " + workoutId);
    	}
    }

    /**
     * Find workout by ID
     * @param id id of workout
     * @return workout if found
     */
    public Workout findById(int id) {
    	logger.debug("Looking up workout by ID: {}", id);
        return workoutRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Workout not found with ID: {}", id);
                // throw exception if workout not found
                return new IllegalArgumentException("Workout not found with ID: " + id);
            });
    }
}