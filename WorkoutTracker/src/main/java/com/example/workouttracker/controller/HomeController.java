/**
 * HomeContoller.java
 * 
 * This controller handles requests to the home page of the workout tracker application.
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"/", "/home"})
    public String homePage() {
        return "home";
    }
}