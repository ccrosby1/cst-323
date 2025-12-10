/**
 * WorkoutController.java
 * 
 * This controller handles workout entity operations for the workout tracker application
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.controller;

import com.example.workouttracker.model.Workout;
import com.example.workouttracker.service.WorkoutService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {
	
	// Logger for WorkoutController
	private static final Logger logger = LoggerFactory.getLogger(WorkoutController.class);

	// DI for WorkouService
    @Autowired
    private WorkoutService workoutService;

    /**
     * Show the create workout form
     * @param model Model to hold workout data
     * @return create workout view
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
    	logger.info("Workout creation form requested");
    	// bind empty object to form
        model.addAttribute("workout", new Workout());
        return "createWorkout";
    }

    /**
     * Handle workout form submission
     * @param workout Workout entity from form
     * @param userDetails Authenticate user details
     * @return redirect to workout list
     */
    @PostMapping("/create")
    public String submitWorkout(@Valid @ModelAttribute Workout workout,
    							BindingResult result,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
    	logger.debug("Submitting new workout for user '{}'", userDetails.getUsername());
    	
    	// check for validation errors
    	if (result.hasErrors()) {
    		logger.warn("Workout submission failed due to validation errors for user '{}'", userDetails.getUsername());
    		model.addAttribute("workout", workout);
			return "createWorkout"; // return to form if errors
    	}
        // save workout with associated user
    	workoutService.saveWorkout(workout, userDetails.getUsername());
    	logger.info("Workout created successfully for user '{}'", userDetails.getUsername());
        return "redirect:/workouts";
    }
    
    /**
     * List workouts for authenticated user with optional search
     * @param query Optional search query
     * @param userDetails Authenticated user details
     * @param model Model to hold workouts
     * @return list workouts view
     */
    @GetMapping
    public String listWorkouts(@RequestParam(value="query", required=false) String query,
    						   @AuthenticationPrincipal UserDetails userDetails, 
    						   Model model) {
    	logger.debug("Listing workouts for user '{}', search query='{}'", userDetails.getUsername(), query);
    	
        // get workouts for user
    	List<Workout> workouts = workoutService.getWorkoutsByUsername(userDetails.getUsername());
       
    	// filter workouts if query provided
    	if (query != null && !query.isBlank()) {
    		logger.info("Filtering workouts for user '{}' with query '{}'", userDetails.getUsername(), query);
    		
            String lowerQuery = query.toLowerCase();
            workouts = workouts.stream()
                .filter(w -> w.getName().toLowerCase().contains(lowerQuery)
                          || w.getMuscleGroup().toLowerCase().contains(lowerQuery)
                          || w.getNotes().toLowerCase().contains(lowerQuery)
                          || w.getDate().toString().toLowerCase().contains(lowerQuery)
                          || String.valueOf(w.getDuration()).contains(lowerQuery))
                .toList();
        }

    	// add workouts and query to model
        model.addAttribute("workouts", workouts);
        model.addAttribute("query", query);
        logger.info("Retrieved {} workouts for user '{}'", workouts.size(), userDetails.getUsername());
        return "listWorkouts";
    }
    
    /**
     * Delete workout by id
     * @param id id of workout to delete
     * @param userDetails Authenticated user details
     * @return redirect to workout list
     */
    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable("id") int id,
    							@AuthenticationPrincipal UserDetails userDetails) {
    	logger.debug("User '{}' attempting to delete workout with ID {}", userDetails.getUsername(), id);
		// delete workout by id, send username for verification
		workoutService.deleteWorkoutById(id, userDetails.getUsername());
		logger.info("Workout with ID {} deleted by user '{}'", id, userDetails.getUsername());
		return "redirect:/workouts";
	}
    
    /**
     * Show edit form for workout
     * @param id id of workout to edit
     * @param userDetails Authenticated user details
     * @param model Model to hold workout data
     * @return edit workout view
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id,
    						   @AuthenticationPrincipal UserDetails userDetails,
							   Model model) {
    	logger.debug("User '{}' requesting edit form for workout ID {}", userDetails.getUsername(), id);
    	// get workout by id
		Workout workout = workoutService.findById(id);
		model.addAttribute("workout", workout);
		return "editWorkout";
	}
    
    /**
     * Handle workout update submission
     * @param id id of workout to update
     * @param workout Updated workout data
     * @param userDetails Authenticated user details
     * @return redirect to workout list
     */
    @PostMapping("/edit/{id}")
    public String updateWorkout(@PathVariable("id") int id,
							 @Valid @ModelAttribute Workout workout,
							 BindingResult result,
							 @AuthenticationPrincipal UserDetails userDetails,
							 Model model) {
    	logger.debug("User '{}' submitting update for workout ID {}", userDetails.getUsername(), id);
    	
		// check for validation errors
    	if (result.hasErrors()) {
    		logger.warn("Workout update failed due to validation errors for user '{}'", userDetails.getUsername());
    		workout.setId(id); // persist id for form
    		model.addAttribute("workout", workout);
    		return "editWorkout"; // return to form 
    	}
    	
    	// ensure the workout being updated has the correct id
		workout.setId(id);
		// save updated workout
		workoutService.saveWorkout(workout, userDetails.getUsername());
		logger.info("Workout with ID {} updated successfully by user '{}'", id, userDetails.getUsername());
		return "redirect:/workouts";
    }
}