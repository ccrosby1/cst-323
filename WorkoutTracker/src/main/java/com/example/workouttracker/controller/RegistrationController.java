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

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid RegistrationDto userDto,
                               BindingResult result,
                               Model model) {

        if (userService.isUsernameTaken(userDto.getUsername())) {
            result.rejectValue("username", null, "Username is already taken");
        }

        if (userService.isEmailTaken(userDto.getEmail())) {
            result.rejectValue("email", null, "Email is already registered");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.registerNewUser(userDto);
        return "redirect:/login?registered";
    }
}