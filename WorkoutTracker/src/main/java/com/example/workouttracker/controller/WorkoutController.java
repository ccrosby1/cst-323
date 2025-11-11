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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

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
    public String submitWorkout(@ModelAttribute Workout workout,
                                @AuthenticationPrincipal UserDetails userDetails) {
        // save workout with associated user
    	workoutService.saveWorkout(workout, userDetails.getUsername());
        return "redirect:/workouts";
    }
    
    /**
     * List workouts for authenticated user
     * @param userDetails Authenticated user details
     * @param model Model to hold workouts
     * @return list workouts view
     */
    @GetMapping
    public String listWorkouts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // get workouts for user
    	List<Workout> workouts = workoutService.getWorkoutsByUsername(userDetails.getUsername());
       
        // Print to console --- REMOVE AFTER TESTING ---
        System.out.println("Workouts for user " + userDetails.getUsername() + ":");
        workouts.forEach(System.out::println);
        
        // pass to view
        model.addAttribute("workouts", workouts);
        return "listWorkouts";
    }
    
    /**
     * Delete workout by id
     * @param id id of workout to delete
     * @param userDetails Authenticated user details
     * @return redirect to workout list
     */
    @PostMapping("/delete/{id}")
    public String DeleteWorkout(@PathVariable("id") int id,
    							@AuthenticationPrincipal UserDetails userDetails) {
		// delete workout by id, send username for verification
		workoutService.deleteWorkoutById(id, userDetails.getUsername());
		return "redirect:/workouts";
	}
}