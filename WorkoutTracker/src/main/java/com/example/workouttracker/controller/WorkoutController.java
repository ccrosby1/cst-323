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

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "createWorkout";
    }

    @PostMapping("/create")
    public String submitWorkout(@ModelAttribute Workout workout,
                                @AuthenticationPrincipal UserDetails userDetails) {
        workoutService.saveWorkout(workout, userDetails.getUsername());
        return "redirect:/workouts";
    }
    
    @GetMapping
    public String listWorkouts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Workout> workouts = workoutService.getWorkoutsByUsername(userDetails.getUsername());
       
     // Print to console
        System.out.println("Workouts for user " + userDetails.getUsername() + ":");
        workouts.forEach(System.out::println);
        
        model.addAttribute("workouts", workouts);
        return "listWorkouts";
    }
}