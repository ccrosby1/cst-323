/**
 * RegistrationController.java
 * 
 * This controller handles user registration for the workout tracker application
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.controller;

import com.example.workouttracker.dto.RegistrationDto;
import com.example.workouttracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

	// Inject UserService
    @Autowired
    private UserService userService;

    /**
     * Display the registration form
     * @param model Model to holed form data
     * @return registration view
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    /**
     * Handle registration form submission
     * @param userDto DTO for user registration data
     * @param result BindingResult for validation
     * @param model Model to hold data
     * @return redirect to login on success or back to register on failure
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid RegistrationDto userDto,
                               BindingResult result,
                               Model model) {

    	// check for existing username
        if (userService.isUsernameTaken(userDto.getUsername())) {
            result.rejectValue("username", null, "Username is already taken");
        }

        // check for existing email
        if (userService.isEmailTaken(userDto.getEmail())) {
            result.rejectValue("email", null, "Email is already registered");
        }

        // if there are validation errors, return to registration form
        if (result.hasErrors()) {
            return "register";
        }

        // register new user
        userService.registerNewUser(userDto);
        return "redirect:/login?registered";
    }
}